package org.zerock.memberboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Mmember extends BaseEntity{ //movie용 Member
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String email;

    private String pw;

    private String nickname;

}
