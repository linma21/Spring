package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.service.CommentService;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comment")
    public ResponseEntity<Article> commentWrite(@RequestBody ArticleDTO articleDTO,
                                                HttpServletRequest req) {
        log.info("commentWrite : " + articleDTO);
        String writer = articleDTO.getWriter();
        String cate = articleDTO.getCate();
        int no = articleDTO.getParent();
        String regIp = req.getRemoteAddr();

        //articleDTO.setUser(userService.selectUser(writer));
        articleDTO.setRegIp(regIp);

        return commentService.insertComment(articleDTO);

       // return "redirect:/article/view?no=" + no + "&cate=" + cate + "&page=" + page;
    }

    @GetMapping("/comment/{parent}")
    public ResponseEntity<List<ArticleDTO>> commentList(@PathVariable("parent") int parent){

        return commentService.selectComments(parent);
        //model.addAttribute("pageResponseDTO", pageResponseDTO);
    }
    @DeleteMapping("/comment/{no}")
    public ResponseEntity<?> deleteComment(@PathVariable("no") int no){
        return commentService.deleteComment(no);
    }
    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody ArticleDTO articleDTO){
        log.info("modifyComment : " +articleDTO.toString());
        return commentService.updateComment(articleDTO);
    }
}
