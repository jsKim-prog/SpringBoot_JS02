package org.zerock.memberboard.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.memberboard.entity.Mmember;
import org.zerock.memberboard.entity.Movie;
import org.zerock.memberboard.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //특정영화에 대한 리뷰
    @EntityGraph(attributePaths = {"mmember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);
    // @EntityGraph : 엔티티의 특정한 속성을 같이 로딩하도록 표시 -> member를 함께 로딩하기 위해 사용
    //type : attributePaths에 표시한 속성은 EAGER, 나머지는 LAZY

    //리뷰삭제-> 회원삭제 시 연동용
    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(Mmember member);
}
