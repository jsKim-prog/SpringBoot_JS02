package org.zerock.memberboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //직접적으로 테이블을 생성하지 않는 엔티티
@EntityListeners(value = {AuditingEntityListener.class}) //감시체제용
@Getter
abstract class BaseEntity { //모든 엔티티의 부모로, 날짜 처리용을 담당
    //abstract --캡슐화
    @CreatedDate //생성시간
    @Column(name = "regdate", updatable = false) //필드명 지정, 업데이트 불가
    private LocalDateTime regDate; //등록일 담당

    @LastModifiedDate //수정시간
    @Column(name = "moddate")
    private LocalDateTime modDate; //수정일 담당
}
