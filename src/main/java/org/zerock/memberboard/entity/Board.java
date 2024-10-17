package org.zerock.memberboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //writer 는 문자열 처리 제외
public class Board extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mariaDB용 자동번호
    private Long bno;
    
    private String title;
    
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //fk 설정(자식이 부모를 선택), 지연로딩
    private Member writer; //Member 테이블의 정보를 가져옴

    //변경위한 메서드 - 제목, 내용만 변경가능
    public void changeTitle(String title){
        this.title=title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}

//Hibernate:
//        create table board (
//        bno bigint not null auto_increment,
//        moddate datetime(6),
//        regdate datetime(6),
//        content varchar(255),
//        title varchar(255),
//        writer_email varchar(255),
//        primary key (bno)
//        ) engine=InnoDB

//Hibernate:
//        alter table if exists board
//        add constraint FK1iu8rhoim4thb0y12cpt01oiu
//        foreign key (writer_email)
//        references member (email)