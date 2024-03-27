package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.Config;
import kr.co.sboard.repository.ConfigRepository;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.CommentService;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ConfigRepository configRepository;

    @GetMapping("/article/list")
    public String list(Model model,
                        String cate,
                        PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = null;

        if(pageRequestDTO.getKeyword() == null) {
            // 일반 글 목록 조회
            pageResponseDTO = articleService.selectArticles(pageRequestDTO);
        }else {
            // 검색 글 목록 조회
            pageResponseDTO = articleService.searchArticles(pageRequestDTO);
        }
        log.info("pageResponseDTO : " + pageResponseDTO);

        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }
    @GetMapping("/article/write")
    public String write(Model model,@ModelAttribute("cate") String cate, PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .build();

        model.addAttribute(pageResponseDTO);
        return "/article/write";
    }
    @GetMapping("/article/like/{no}")
    public ResponseEntity<?> articleLike(@PathVariable("no") int no){
        return null;
    }
    @PostMapping("/article/write")
    public String write(@ModelAttribute("writer") String writer, HttpServletRequest req, ArticleDTO articleDTO){
        // 전송된 사용자 식별자를 사용하여 사용자 정보를 가져옴
        //User user = modelMapper.map(userService.selectUser(writer), User.class);
        //articleDTO.setUser(userService.selectUser(writer));
        String regIp = req.getRemoteAddr();
        articleDTO.setRegIp(regIp);

        articleService.insertArticle(articleDTO);
        return "redirect:/article/list?cate="+articleDTO.getCate();
    }
    @PostMapping("/article/modify")
    public String modify(PageResponseDTO pageResponseDTO, ArticleDTO articleDTO){
        // 전송된 사용자 식별자를 사용하여 사용자 정보를 가져옴
        //User user = modelMapper.map(userService.selectUser(writer), User.class);
        //articleDTO.setUser(userService.selectUser(writer));

        articleService.updateArticle(articleDTO);

        int pg = pageResponseDTO.getPg();
        int no = articleDTO.getNo();
        String cate = pageResponseDTO.getCate();
        return "redirect:/article/view?no=" + no + "&pg=" + pg + "&cate=" + cate;
    }
    @GetMapping("/article/view")
    public String view(Model model,
                       String cate,
                       PageRequestDTO pageRequestDTO){
        log.info("viewCont : " + pageRequestDTO.toString());
        int no = pageRequestDTO.getNo();
        int pg = pageRequestDTO.getPg();
        // 글 조회
        ArticleDTO article = articleService.selectArticle(no);
        // 댓글 조회
        //List<ArticleDTO> comments = commentService.selectComments(no);
        log.info("viewCont article1 : " + article.toString());
        PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                //.dtoList(comments)
               // .total(comments.size())
                .build();
        log.info("viewCont article2 : " + article.toString());
        log.info("pageResponseDTO : " + pageResponseDTO.toString());
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("article", article);
        return "/article/view";
    }
    @PutMapping("/article")
    public ResponseEntity<?> modifyArticle(@RequestParam("files") MultipartFile[] files,
                                           @RequestBody ArticleDTO articleDTO) {

        log.info("modifyArticle : " + articleDTO.toString());
        return articleService.updateArticle(articleDTO);
    }

    @DeleteMapping("/article/{no}")
    public ResponseEntity<?> deleteArticle(@PathVariable("no") int no){
        return articleService.deleteArticle(no);
    }

}
