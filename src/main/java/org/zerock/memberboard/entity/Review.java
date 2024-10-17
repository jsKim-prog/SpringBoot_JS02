package org.zerock.memberboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "movie"})
public class Review extends BaseEntity{ //mapping table : Mmember + Movie
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie; //fk(pk:Long mno)

    @ManyToOne(fetch = FetchType.LAZY)
    private Mmember member; //fk(pk: Long mid)

    private int grade;

    private String text;
}
