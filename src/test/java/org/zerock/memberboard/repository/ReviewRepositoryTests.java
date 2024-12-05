package org.zerock.memberboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.memberboard.entity.Mmember;
import org.zerock.memberboard.entity.Movie;
import org.zerock.memberboard.entity.Review;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository repoReview;

    @Test //더미데이터
    public void insertReview(){
        //200개 등록
        //영화번호(mno) : 1~100 random / 리뷰어번호(mid) :1~100 random
        IntStream.rangeClosed(1, 200).forEach(i ->{
            //영화번호, 리뷰어 번호
            Long mno = (long)(Math.random()*100)+101; //101~200
            Long mid = (long)(Math.random()*100)+101;
            Mmember mmember = Mmember.builder().mid(mid).build();
            Movie movie = Movie.builder().mno(mno).build();

            Review review = Review.builder()
                    .member(mmember).movie(movie)
                    .grage((int)(Math.random()*5)+1).text("영화소감..."+i)
                    .build();
            repoReview.save(review);
        });
    }

    @Test //데이터 리셋
    public void truncateReview(){
        repoReview.deleteAll();
    }
}
