package org.zerock.memberboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.dto.BoardDTO;
import org.zerock.memberboard.dto.PageRequestDTO;
import org.zerock.memberboard.dto.PageResultDTO;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Member;
import org.zerock.memberboard.repository.BoardRepository;
import org.zerock.memberboard.repository.ReplyRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository; //삭제시 연동 필요

    @Override
    public Long register(BoardDTO dto) {
        log.info("Board register에서 받은 dto"+dto);
        Board board  = dtoToEntity(dto);
        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

//       Page<Object[]> result = boardRepository.getBoardWithReplyCount(
//               pageRequestDTO.getPageable(Sort.by("bno").descending())  );
        //SearchBoardRepositoy 상속, 활용
        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) { //게시물 삭제
        //1. ReplyRepository(댓글 먼저 삭제) -> 2. 게시글 삭제
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);

    }

    @Override
    public void modify(BoardDTO boardDTO) {
        //entity 가져와서 제목, 내용만 dto 것으로 바꾼 후 다시 repository로
        Board board = boardRepository.getOne(boardDTO.getBno()); //지연로딩 위해 getOne() 사용
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        boardRepository.save(board);
    }
}
