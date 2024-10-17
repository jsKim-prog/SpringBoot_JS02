package org.zerock.memberboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.memberboard.dto.MovieDTO;
import org.zerock.memberboard.entity.Movie;
import org.zerock.memberboard.entity.MovieImage;
import org.zerock.memberboard.repository.MovieImageRepository;
import org.zerock.memberboard.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {
        //인터페이스에서 dtoToEntity 로 Map 에 넣은 것을 받아 다시 분리
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imageList");

        movieRepository.save(movie); //영화등록
        movieImageList.forEach(movieImage ->{
            imageRepository.save(movieImage); //이미지(리스트) 등록
        });
        
        return movie.getMno();
    }
}
