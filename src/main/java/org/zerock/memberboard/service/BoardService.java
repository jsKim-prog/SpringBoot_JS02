package org.zerock.memberboard.service;

import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.dto.PageResultDTO;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Member;

public interface BoardService {
    //게시물 등록
    Long register(BoardDTO dto);

    //목록조회(페이징)
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //게시물 조회
    BoardDTO get(Long bno);

    //게시글 삭제(1: 댓글먼저 삭제(ReplyRepository)->여기)
    void removeWithReplies(Long bno);

    //게시글 변경(제목, 내용만)
    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){ //연관관계 가진 member entity까지 처리 ->register 에서 사용
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();

        return boardDTO;
    }
}
