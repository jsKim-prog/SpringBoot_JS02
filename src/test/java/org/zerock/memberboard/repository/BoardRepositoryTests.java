package org.zerock.memberboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.entity.Board;
import org.zerock.memberboard.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
@Transactional
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test //더미데이터
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });

    }

}
