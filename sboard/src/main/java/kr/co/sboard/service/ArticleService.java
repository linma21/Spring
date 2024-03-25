package kr.co.sboard.service;

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
    public PageResponseDTO findByParentAndCate(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");
        String cate = pageRequestDTO.getCate();
        Page<Article> pageArticles = articleRepository.findByParentAndCate(0, pageRequestDTO.getCate(), pageable);

        List<ArticleDTO> dtoList = pageArticles.getContent().stream()
                .map(entity -> modelMapper.map(entity, ArticleDTO.class))
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

        Optional<Article> optArticle = articleRepository.findById(articleDTO.getNo());

        if(optArticle.isPresent()){
            // 파일 업로드
            List<FileDTO> files = fileService.fileUpload(articleDTO);

            Article article = optArticle.get();
            article.setFile(files.size() + article.getFile());
            article.setContent(articleDTO.getContent());
            log.info("updateArticle ...3 : "+ article);

            Article modifiedArticle = articleRepository.save(article);
            log.info("updateArticle ...4 : "+ article);

            // 파일 insert
            for(FileDTO fileDTO : files){
                fileDTO.setAno(modifiedArticle.getNo());
                File file = modelMapper.map(fileDTO, File.class);

                fileRepository.save(file);
            }

            return ResponseEntity.ok().body(modifiedArticle);
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
