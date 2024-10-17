package org.zerock.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;

    private String type;
    private String keyword;

    //기본생성자
    public PageRequestDTO(){
        this.page = 1; //기본 페이지 번호
        this.size=10; //기본 게시물수
    }

    //메서드
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
        // Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
    }
}
