package org.zerock.memberboard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.memberboard.entity.Board;

public interface SearchBoardRepository {
    Board search1();
    
    //페이징 객체 처리 - JPQL 활용
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
