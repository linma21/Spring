package kr.co.ch07.repository.board;

import jakarta.transaction.Transactional;
import kr.co.ch07.entity.board.Article;
import kr.co.ch07.entity.board.Comment;
import kr.co.ch07.entity.board.File;
import kr.co.ch07.entity.board.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    @Test
    public void insertUser(){
        User user = User.builder()
                .uid("a101")
                .name("김춘추")
                .hp("010-1213-2233")
                .build();

        userRepository.save(user);
    }

    public void insertArticle(){
        User user = User.builder().uid("a101").build();

        Article article = Article.builder()
                .title("제목3 입니다")
                .content("내용3")
                .user(user)
                .build();

        articleRepository.save(article);
    }

    public void insertComment(){
        User user = User.builder().uid("a101").build();
        Article article = Article.builder().no(3).build();
        Comment comment = Comment.builder()
                .content("댓글 내용1")
                .article(article)
                .user(user)
                .build();

        commentRepository.save(comment);
    }

    public void insertFile(){
        Article article = Article.builder().no(2).build();
        File file = File.builder()
                .oName("원래파일명.txt")
                .sName("abcd1234.txt")
                .article(article)
                .build();

        fileRepository.save(file);
    }
    /*
        연관관계로 생성된 엔티티를 조회할 때 하나 이상의 SELECT가 실행되기 때문에
        @Transactional 선언으로 한번의 실행으로 처리해야 no session 에러 방지
     */

    @Transactional
    public void selectArticles(){

        List<Article> articles = articleRepository.findAll();

        for(Article article: articles){
            log.info(article.toString());
        }
    }

}