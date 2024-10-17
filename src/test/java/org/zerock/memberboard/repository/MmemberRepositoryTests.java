package org.zerock.memberboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.memberboard.entity.Mmember;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MmemberRepositoryTests {
    @Autowired
    private MmemberRepository repoMmember;

    @Test //더미데이터
    public void insertMembers(){
        IntStream.rangeClosed(1, 100).forEach(i->{
            Mmember mmember = Mmember.builder()
                    .email("r"+i+"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i)
                    .build();
            repoMmember.save(mmember);
        });
    }

    @Test //movie 데이터 리셋
    public void truncateMembers(){
        repoMmember.deleteAll();
    }
}
