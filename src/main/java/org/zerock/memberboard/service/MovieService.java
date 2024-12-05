package org.zerock.memberboard.service;

import org.zerock.memberboard.dto.MovieDTO;
import org.zerock.memberboard.dto.MovieImageDTO;
import org.zerock.memberboard.entity.Movie;
import org.zerock.memberboard.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    //영화등록 : MovieDTO -> entity(jpa이용) -> save

    Long register(MovieDTO movieDTO);

    //MovieImageDTO 함께 처리 -> Map 타입 반환
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
        Map<String, Object> entityMap = new HashMap<>();

        //movie 처리
        Movie movie = Movie.builder()
                .mno(movieDTO.getMno()).title(movieDTO.getTitle())
                .build();
        entityMap.put("movie", movie);
        //image 처리
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size()>0){
            List<MovieImage> imageENList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(movieImageDTO.getUuid())
                        .imgName(movieImageDTO.getImgName())
                        .path(movieImageDTO.getPath())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imageList", imageENList);
        }

        return entityMap;
    }
}
