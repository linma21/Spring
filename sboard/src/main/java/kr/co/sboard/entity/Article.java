package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "article")
public class Article {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int parent;
    private int comment;
    private String cate;

    @Column(nullable = true)
    private String title;

    private String content;
    private int file;
    private int hit;
    private int heart;

    private String writer;

    private String regIp;
    @CreationTimestamp
    private LocalDateTime rDate;

    @OneToMany(mappedBy = "ano")
    private List<File> fileList;

    private String nick;

}
