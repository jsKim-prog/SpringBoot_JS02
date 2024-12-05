package org.zerock.memberboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.entity.Movie;
import org.zerock.memberboard.entity.MovieImage;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository repoMovie;
    @Autowired
    private MovieImageRepository repoMovieImg;

    @Transactional
    @Commit
    @Test //더미데이터
    public void insertMovies(){
        IntStream.rangeClosed(1, 100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("영화제목"+i)
                    .build();
            System.out.println("-----------------------------------");
            repoMovie.save(movie);
            int count = (int)(Math.random()*5)+1; //1, 2, 3, 4(이미지 개수 최대 5개)
            for(int j=0; j<count;j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString()).movie(movie).imgName("test"+j+".jpg")
                        .build();
                repoMovieImg.save(movieImage);
            }
            System.out.println("-----------------------------------");
        });
    }

    @Test //movie 데이터 리셋
    public void truncateMovie(){
        repoMovie.deleteAll();
    }

    @Test //movieImg 데이터 리셋
    public void truncateMovieImg(){
        repoMovieImg.deleteAll();

    }
}
