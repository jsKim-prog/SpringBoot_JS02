package org.zerock.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long mno;
    private String title;

    @Builder.Default //특정 필드를 특정 값으로 초기화할 때
    private List<MovieImageDTO> imageDTOList = new ArrayList<>(); //이미지 리스트
}
