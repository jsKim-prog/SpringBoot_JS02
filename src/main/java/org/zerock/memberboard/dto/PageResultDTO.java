package org.zerock.memberboard.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList; //제너릭으로 받은 DTO를 리스트 처리

    private int totalPage; //총 페이지 번호
    private int page; //현재 페이지 번호
    private int size; //목록 사이즈(기본값 : 10)

    private int strat, end; //시작, 끝 페이지 번호
    private boolean prev, next; //이전, 다음 버튼 유무
    private List<Integer> pageList; //페이지 번호 목록(1, 2, 3, 4, 5...-> 하단번호)

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){ //EN(엔티티)를 함수형으로 처리
        //Function<EN, DTO> fn -> 엔티티 객체들을 dto로 변환
        //나중에 어떤 종류의 Page<E> 타입이 생성되더라도 처리 가능함
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        //추가
        totalPage = result.getTotalPages(); //p.68 참고(총 페이지)
        makePageList(result.getPageable()); //아래쪽에 메서드 생성

    }

    private void makePageList(Pageable pageable){
        //페이지 리스트 생성용 전용 코드
        this.page = pageable.getPageNumber()+1; //0부터 시작함
        this.size = pageable.getPageSize();
        //계산용 페이지
        int tempEnd = (int) (Math.ceil(page/10.0))*10;
        strat = tempEnd -9;
        prev=strat>1; // 시작페이지가 1보다 크면 있음
        end = totalPage > tempEnd? tempEnd:totalPage;
        next = totalPage>tempEnd; //next버튼
        pageList = IntStream.rangeClosed(strat, end).boxed().collect(Collectors.toList()); //페이징용 가로 리스트 결과
        //prev(t/f) 1 2 3 4 5 6 7 8 9 10 next(t/f)
    }
}
