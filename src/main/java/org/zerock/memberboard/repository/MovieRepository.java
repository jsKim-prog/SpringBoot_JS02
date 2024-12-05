package org.zerock.memberboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.memberboard.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    //Movie List 호출(+Paging) : movie+movieImg+Review
    @Query("select m, mi,avg (coalesce(r.grade,0) ), count (distinct r) from Movie m " + "left outer join MovieImage mi on mi.movie=m " +
            "left outer join Review r on r.movie=m group by m")
    Page<Object[]> getListPage(Pageable pageable);
    //"select m, max (mi),avg (coalesce(r.grade,0) ), count (distinct r) from Movie m "+"left outer join MovieImage mi on mi.movie=m " + "left outer join Review r on r.movie=m group by m" -- 1 movie X N Img  X N Review 실행 -> 1 IMG로 수정(가장 먼저 입력된 이미지 연결)

    //특정 영화 조회
    @Query("select m, mi, avg (coalesce(r.grade,0)), count (r)"
            + "from Movie  m left outer join MovieImage mi on mi.movie=m "
            +"left outer join Review r on r.movie=m "
            + "where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);

}
