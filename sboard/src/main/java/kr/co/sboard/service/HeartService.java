package kr.co.sboard.service;

import kr.co.sboard.dto.HeartDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.Heart;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<?> insert(HeartDTO heartDTO){

        log.info("insert ...1 ");
        Heart heart = null;
        heart = heartRepository.findByUidAndNo(heartDTO.getUid(), heartDTO.getNo());
        //log.info("insert ...2 "+heart.getUid());

        if(heart == null){
            // like insert
            Heart saveHeart = modelMapper.map(heartDTO, Heart.class);
            heartRepository.save(saveHeart);
            log.info("insert ...3 " +saveHeart.toString());
            // article like count ++
            Article article = articleRepository.findById(heartDTO.getNo()).get();
            article.setHeart(article.getHeart() + 1);
            articleRepository.save(article);
            log.info("insert ...4 " +article.getHeart());
            return ResponseEntity.ok().body(article);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body("already like");
    }
    @Transactional
    public ResponseEntity<?> delete(String uid, int no){
        log.info("deleteHeartSer ...1 : " + uid);
        log.info("deleteHeartSer ...2 : " + no);
        heartRepository.deleteByUidAndNo(uid, no);

        // article heart count --
        Article article = articleRepository.findById(no).get();
        log.info("deleteHeartSer ...3 : " + article);
        article.setHeart(article.getHeart() - 1);
        articleRepository.save(article);

        return ResponseEntity.ok().body(article);
    }
}
