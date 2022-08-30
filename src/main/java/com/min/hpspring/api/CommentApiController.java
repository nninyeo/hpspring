package com.min.hpspring.api;

import com.min.hpspring.entity.Comment;
import com.min.hpspring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletionService;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;


    /** 댓글 목록 조회: GET - api/articles/{article_id}/comments */
    @GetMapping("api/articles/{article_id}/comments")
    public List<Comment> comments() {    //일단 네이밍은 예제대로 하자. ㅠㅠ
        //서비스에게 위임

        //결과 응답
        return null;
    }


    /** 댓글 생성  */


    /** 댓글 수정 */


    /** 댓글 삭제 */




}//CommentApiController