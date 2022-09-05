package com.min.hpspring.api;

import com.min.hpspring.dto.CommentDto;
import com.min.hpspring.entity.Comment;
import com.min.hpspring.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletionService;

@Slf4j
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
        //이건에러,우리가 예상하는건 <Obj>인데 실제 반환타입은 <List 댓글Dto>타입이라기때문에다. ??? 흠..

    }


    /** 댓글 생성
     * @Param articleId: 댓글이 달리는 원본글 id, dto: 댓글의 내용물들
     * @Return 성공결과+ResponseEntity의 body
     * @throws 처리없음
     * */
    /* API ex: POST - http://localhost:8080/api/articles/1/comments
        {
          "nickname": "ex",
          "body": "ex",
          "article_id": "1"   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
              id: 댓글의 id는 보내지않는다.
              ((카멜케이스는 @JsonProperty("article_id")해서 작동안될거임.))
         }*/
    //댓글에 대한 게시물 번호가 있을테니 articleId가 있는것이다.

    @PostMapping("api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto) {

        log.info("dto>>>>>>>>>>>>>>>>>>" + dto.toString());


        //폼데이터 가져와야함.

        //게시글 조회??상관없지않나 파라미터들 (id, 게시글내용, 닉넴) 쏴주고 리턴받음 되는거아닌가
        CommentDto created = commentService.create(articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(created);

    }





    /** 댓글 수정
     * * @Param
     * @Return
     * @throws
     * */
    /* API ex: PATCH - http://localhost:8080/api/articles/1/comments
        {
          "id": 1,
          "nickname": "닉네임변경(null가능)",
          "body": "댓글변경(null가능)",
           "article_id": 4
        }*/
    //댓글에 대한 게시물 번호가 있을테니 articleId가 있는것이다.
    @PatchMapping("/api/comments/{id}") //{id}는 댓글의 id다.
    public ResponseEntity<CommentDto> update(@PathVariable Long id, //왜 articleId가아닐까
                                             @RequestBody CommentDto dto){

        CommentDto updatedDto = commentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }


    /** 댓글 삭제
     * * @Param
     * @Return
     * @throws
     * */
    /* API ex: DELETE - http://localhost:8080/api/comments/1
        {
          "id": 1,
          "nickname": "ex nick",
          "body": "ex body",
          "articleId": "4"
         }*/
    //댓글에 대한 게시물 번호가 있을테니 articleId가 있는것이다.
    @DeleteMapping("/api/comments/{id}") //{id}는 댓글의 id다.
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){

        CommentDto deleteDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }







}//CommentApiController