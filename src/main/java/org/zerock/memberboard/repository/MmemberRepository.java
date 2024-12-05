package org.zerock.memberboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.memberboard.entity.Mmember;

public interface MmemberRepository extends JpaRepository<Mmember, Long> {
}
