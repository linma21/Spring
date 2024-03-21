package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.CommentService;
import kr.co.sboard.service.FileService;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/article/list")
    public String list(PageRequestDTO pageRequestDTO
                        , Model model){
        PageResponseDTO pageResponseDTO = articleService.findByParentAndCate(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);

        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }
    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        return "/article/write";
    }
    @PostMapping("/article/write")
    public String write(@ModelAttribute("writer") String writer, HttpServletRequest req, ArticleDTO articleDTO){
        // 전송된 사용자 식별자를 사용하여 사용자 정보를 가져옴
        //User user = modelMapper.map(userService.selectUser(writer), User.class);
        articleDTO.setUser(userService.selectUser(writer));
        String regIp = req.getRemoteAddr();
        articleDTO.setRegIp(regIp);

        articleService.insertArticle(articleDTO);
        return "redirect:/article/list?cate="+articleDTO.getCate();
    }
    @GetMapping("/article/view")
    public String view(PageRequestDTO pageRequestDTO,
                       Model model){
        log.info("viewCont : " + pageRequestDTO.toString());
        int no = pageRequestDTO.getNo();
        int pg = pageRequestDTO.getPg();
        String cate = pageRequestDTO.getCate();
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

}
