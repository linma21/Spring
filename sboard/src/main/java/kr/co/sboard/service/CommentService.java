package kr.co.sboard.service;

import com.querydsl.core.Tuple;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    @Transactional
    public ResponseEntity<Article> insertComment(ArticleDTO articleDTO){
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info("insertComment : " + article.toString());
        articleRepository.incrementCommentByNo(article.getParent());
        Article saveArticle = articleRepository.save(article);

        return ResponseEntity.ok().body(saveArticle);
    }

    public ResponseEntity<List<ArticleDTO>> selectComments(int parent){
        log.info("selectComments ...1: " + parent);
        // ArticleRepository > findByParent() 쿼리 메서드 정의
        List<Tuple> articleList = articleRepository.selectComments(parent);

        List<ArticleDTO> articleDTOS = articleList.stream()
                .map(tuple -> {
                    log.info("tuple : "+ tuple);
                    Article article = tuple.get(0, Article.class);
                    String nick = tuple.get(1, String.class);
                    article.setNick(nick);
                    log.info("article : "+ article);
                    return modelMapper.map(article, ArticleDTO.class);
                })
                .toList();

        return ResponseEntity.ok().body(articleDTOS);
    }

    public ResponseEntity<List<ArticleDTO>> selectComments2(int parent){
        log.info("selectComments ...1: " + parent);
        // ArticleRepository > findByParent() 쿼리 메서드 정의
        List<Article> articleList = articleRepository.findByParent(parent);

        List<ArticleDTO> articleDTOS = articleList.stream()
                .map(entity -> modelMapper.map(entity, ArticleDTO.class))
                .toList();

        return ResponseEntity.ok().body(articleDTOS);
    }

    public ResponseEntity<?> updateComment(ArticleDTO articleDTO){
        log.info("updateComment ...1 : " +articleDTO.toString());
        Optional<Article> optArticle = articleRepository.findById(articleDTO.getNo());

        log.info("updateComment ...2 optArticle :" + optArticle);

        if(optArticle.isPresent()){
            // 댓글 수정
            Article article = optArticle.get();
            article.setContent(articleDTO.getContent());

            log.info("updateComment ...3 : "+ article);

            Article modifiedArticle = articleRepository.save(article);
            log.info("updateComment ...4 : "+ modifiedArticle);
            // 수정 후 데이터 반환
            return ResponseEntity.ok().body(modifiedArticle);

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteComment(int no) {

        log.info("deleteComment no :" + no);

        Optional<Article> optArticle = articleRepository.findById(no);

        log.info("deleteComment optArticle :" + optArticle);

        if(optArticle.isPresent()){
            // 댓글 삭제
            articleRepository.deleteById(no);

            // 원글 comment --
            int pno = optArticle.get().getParent();
            Article article = articleRepository.findById(pno).get();
            int comment = article.getComment();
            article.setComment(comment - 1);
            articleRepository.save(article);

            return ResponseEntity.ok().body(optArticle.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }
}
