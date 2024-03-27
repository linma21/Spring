package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "heart")
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hno;
    private String uid;
    private int no;
}
