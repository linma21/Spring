package kr.co.sboard.service;

import com.querydsl.core.Tuple;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.File;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public void insertArticle(ArticleDTO articleDTO){

        List<FileDTO> files = fileService.fileUpload(articleDTO);

        // 파일 개수
        articleDTO.setFile(files.size());

        Article article = modelMapper.map(articleDTO, Article.class);

        // 저장 후 저장한 엔티티 객체 반환(JPA sava() 메서드는 default로 저장한 Entity를 반환)
        Article saveArticle = articleRepository.save(article);
        log.info("insertArticle : " + saveArticle.toString());

        // 파일 insert
        for(FileDTO fileDTO : files){
            fileDTO.setAno(saveArticle.getNo());
            File file = modelMapper.map(fileDTO, File.class);

            fileRepository.save(file);
        }

    }


    @Transactional
    public ArticleDTO selectArticle(int no){
        Optional<Article> optArticle = articleRepository.findById(no);
        log.info("selectArticle ... 1 : " + optArticle.toString());

        ArticleDTO articleDTO = null;

        if(optArticle.isPresent()){
            Article article = optArticle.get();
            log.info("selectArticle ... 2 : " + article.toString());
            articleDTO = modelMapper.map(article, ArticleDTO.class);
            log.info("selectArticle ... 3 articleDTO1 : " + articleDTO.toString());
            // hit ++
            articleRepository.incrementHitByNo(no);
        }
        log.info("selectArticle ... 4 articleDTO2 : " + articleDTO.toString());
        return articleDTO;
    }

    /*
    public List<ArticleDTO> selectArticles(){
        List<Article> articles = articleRepository.findAll();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return  modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());
    }
    */
    public PageResponseDTO selectArticles(PageRequestDTO pageRequestDTO){

        log.info("selectArticles...1");
        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("selectArticles...2");
        String cate = pageRequestDTO.getCate();


        Page<Tuple> pageArticles = articleRepository.selectArticles(pageRequestDTO, pageable);

        log.info("selectArticles...3" + pageArticles);
        List<ArticleDTO> dtoList = pageArticles.getContent().stream()
                .map(tuple ->{
                    log.info("tuple : "+ tuple);
                    Article article = tuple.get(0, Article.class);
                    String nick = tuple.get(1, String.class);
                    article.setNick(nick);

                    log.info("article : "+ article);

                    return modelMapper.map(article, ArticleDTO.class);
                })
                .toList();
        log.info("selectArticles...4" +dtoList );
        int total = (int) pageArticles.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    public PageResponseDTO searchArticles(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticles = articleRepository.searchArticles(pageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticles.getContent().stream()
                .map(tuple ->{
                    log.info("tuple : "+ tuple);
                    Article article = tuple.get(0, Article.class);
                    String nick = tuple.get(1, String.class);
                    article.setNick(nick);
                    log.info("article : "+ article);
                    return modelMapper.map(article, ArticleDTO.class);
                })
                .toList();

        int total = (int) pageArticles.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    @Transactional
    public ResponseEntity<?> updateArticle(ArticleDTO articleDTO){
        log.info("updateArticle : " + articleDTO.toString());

        List<FileDTO> files = fileService.fileUpload(articleDTO);

        Article article = articleRepository.findById(articleDTO.getNo()).get();
        if(article != null){
            article.setFile(article.getFile() + files.size());
            article.setContent(articleDTO.getContent());
            // 저장 후 저장한 엔티티 객체 반환(JPA save() 메서드는 default로 저장한 Entity를 반환)
            Article updateArticle = articleRepository.save(article);
            log.info("updateArticle : " + updateArticle.toString());

            // 파일 insert
            for(FileDTO fileDTO : files){
                fileDTO.setAno(updateArticle.getNo());
                File file = modelMapper.map(fileDTO, File.class);

                fileRepository.save(file);
            }
                return ResponseEntity.ok().body(updateArticle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
    }
    @Transactional
    public ResponseEntity<?> deleteArticle(int no){

        log.info("deleteArticle no :" + no);
        Optional<Article> optArticle = articleRepository.findById(no);
        log.info("deleteArticle optArticle :" + optArticle.toString());
        if (optArticle.isPresent()){
            // 파일 삭제
            Article article = optArticle.get();
            if(article.getFile() > 0) {
                fileRepository.deleteFilesByAno(no);
            }
            // 댓글 삭제
            if(article.getComment() > 0) {
                articleRepository.deleteArticlesByParent(no);
            }
            // 게시글 삭제
            articleRepository.deleteById(no);

            return ResponseEntity.ok().body(optArticle.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }
}
