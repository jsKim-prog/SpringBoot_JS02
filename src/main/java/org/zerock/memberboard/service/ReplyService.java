package org.zerock.memberboard.service;

import org.zerock.memberboard.dto.ReplyDTO;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Reply;

import java.util.List;

public interface ReplyService {

    //CRUD
    //댓글등록
    Long register(ReplyDTO replyDTO);

    //조회 - 게시물별(리스트)
    List<ReplyDTO> getList(Long bno);

    //댓글 수정
    void modify(ReplyDTO replyDTO);

    //댓글 삭제
    void remove(Long rno);


    //ReplyDTO -> Reply(EN) : Board 객체 같이 변환
    //Reply(EN) : Board(EN) -> N:1 참조
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();
        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }

    //Reply(EN) -> ReplyDTO : Board 객체 없이 bno만 필요
    //Reply(EN) : Board(EN) -> N:1 참조
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }


}
