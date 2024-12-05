package org.zerock.memberboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.memberboard.dto.ReplyDTO;
import org.zerock.memberboard.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService serviceReply;

    //게시물별 댓글 목록 보이기
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        log.info("받은 bno : "+bno);
        return new ResponseEntity<>(serviceReply.getList(bno), HttpStatus.OK);
    }
    
    //댓글 등록
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        //@RequestBody : JSON -> 객체 자동매핑
        log.info("등록 댓글 dto : "+replyDTO);
        Long rno = serviceReply.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        log.info("삭제 댓글 rno : "+rno);
        serviceReply.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        log.info("수정 댓글 dto : "+replyDTO);
        serviceReply.modify(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
