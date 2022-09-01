package com.min.hpspring.api;

import com.min.hpspring.dto.CommentDto;
import com.min.hpspring.entity.Comment;
import com.min.hpspring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletionService;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;


    /** 댓글 목록 조회: GET - api/articles/{article_id}/comments */
    @GetMapping("api/articles/{article_id}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long article_id) {
        //코멘트의 리스트들을 반환하자. 근데 dto로해야지 당연히. 그래서 CommentDto형을 써야한다.
        //상태값을 같이 보내기위해 ResponseEntity<>로 한번 더 감쌋다.

        //서비스에게 위임하기
        List<CommentDto> dtos = commentService.comments(article_id);//언더바_오타인데 따라가자.

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
        //이건에러,우리가 예상하는건 <Obj>인데 실제 반환타입은 <List 댓글Dto>타입이라기때문에다. ???



    }


    /** 댓글 생성  */


    /** 댓글 수정 */


    /** 댓글 삭제 */




}//CommentApiController