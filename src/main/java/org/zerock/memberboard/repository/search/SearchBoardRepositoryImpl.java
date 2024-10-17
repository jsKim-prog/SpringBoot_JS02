package org.zerock.memberboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.QBoard;
import org.zerock.memberboard.entity.QMember;
import org.zerock.memberboard.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{
    //QuerydslRepositorySupport 상속 -> super() 이용한 호출
    public SearchBoardRepositoryImpl() { 
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("SearchBoard - search1.....");
        QBoard board=QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //테이블 조인(JPQL)
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member)); //추가
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        //select board from Board board left join Reply reply with reply.board = board where board.bno = ?

        //Tuple 객체 사용-join 테이블 검색 결과 담기
        JPQLQuery<Tuple> tuple =  jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);
        log.info("=============================");
        log.info(jpqlQuery);
        log.info("=============================");

        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("SearchBoard - searchPage.....");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //검색어 처리
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L); //빠른검색
        builder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t: typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }//--switch()
            }//--for()
            builder.and(conditionBuilder);
        }

        tuple.where(builder);

        //정렬 --- order by
        Sort sort = pageable.getSort();
        //tuple.orderBy(board.bno.desc()); --직접 코드로 처리시
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            //PathBuilder : Sort 객체의 속성(bno, title) 처리용-> 문자열 이름은 JPQLQuery 변수명과 동일해야 함
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(board);

        //페이지처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();
        log.info(result);

        long count = tuple.fetchCount();
        log.info("count : "+count);

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()),pageable,count);
    }
}
