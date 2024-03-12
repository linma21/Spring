package kr.co.ch07.entity.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT
    private int no;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 연관 엔티티가 로딩될 때 필요 시점에 로딩되는 지연 전략
    @JoinColumn(name = "writer")        // 엔티티가 테이블로 생성 될 때 컬럼명
    private User user;

    // 관계 주인이 아님을 설정 - 외래키가 있는 곳이 주인 (항상 N쪽이 외래키를 가진다.)
    // 양방향 연관관계에서 외래키를 갖는 엔티티의 속성을 mappedBy = "주인테이블???"
    @OneToOne(mappedBy = "article")
    private File file;

    @OneToMany(mappedBy = "article")
    private List<Comment> comment;

    @CreationTimestamp
    private LocalDateTime rdate;
}
