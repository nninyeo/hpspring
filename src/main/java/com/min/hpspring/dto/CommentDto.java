package com.min.hpspring.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.min.hpspring.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


//아래4개는 사실상필수
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    //이 DTO는 json과 컨트롤러 사이의 데이터그릇임.

    //아래4개도 여기필요한것들이니 필수
    private Long id;

    @JsonProperty("article_id")//언더바 카멜로 자동매핑.. 대신 카멜이안됨. @.@
    private Long articleId;//근데 이게작동을안함..

    private String nickname;

    private String body;

    /** Comment Entity -> CommentDto Converter */
    //이 메서드는 static이 붙어있으니 클래스메소드다.
    //인풋이 Comment형이고 아웃풋이 CommentDto형인걸 볼수있다.
    public static CommentDto createCommentDto(Comment comment) {

        return new CommentDto(  //()안에 여러 값들을 넣어야함.

                //comment.에 붙은 ()들은 Comment.java (엔티티)이다. 엔티티의 Getter를 사용해 변환한다.
                comment.getId(),  //일단 코멘트id있어야겠고
                comment.getArticle().getId(), //코멘트의 article을 가져와서 거기서 id만 다시가져오고
                comment.getNickname(),  //나머지것들 가져오고.
                comment.getBody()
        );
    }

//    /**CommentDto Converter ->  Comment Entity */
//    //?
//    public static Comment toEntity(){
//
//        return new Comment(thisid, articleId, )
//    }
//


}
