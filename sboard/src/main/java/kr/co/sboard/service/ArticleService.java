package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public void insertArticle(ArticleDTO articleDTO){
        log.info("insertArticle articleDTO : " +articleDTO.toString());
        // articleDTO를 articleEntity로 변환
        // 정확하게 일치하는 필드만 매핑
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info("insertArticle : " + article.toString());
        articleRepository.save(article);
    }
    public ArticleDTO selectArticle(){
        return null;
    }
    /*
    public List<ArticleDTO> selectArticles(){
        List<Article> articles = articleRepository.findAll();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return  modelMapper.map(articles, new TypeToken<List<ArticleDTO>>() {}.getType());
    }
    */
    public Page<ArticleDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<Article> articles = articleRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "no")));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Page<ArticleDTO> articleDTOs = modelMapper.map(articles, new TypeToken<Page<ArticleDTO>>() {}.getType());

        return articleDTOs;
    }
}
