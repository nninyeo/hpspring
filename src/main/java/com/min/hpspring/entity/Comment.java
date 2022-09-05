package com.min.hpspring.entity;

import com.min.hpspring.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


@Entity //엔티티는 엔티티니깐 엔티티라고 선언
@Getter//기본선언들
@ToString//기본선언들
@AllArgsConstructor//기본선언들
@NoArgsConstructor//기본선언들
public class Comment {

    @Id //이거 자바꺼로해야 오류가안뜨네..하아.. 이게 PK임.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    log.info("a"); 엔티티에서 로그불가. 이건 선언부라 안되나봄.
    @ManyToOne //해당 댓글 엔티티 여러개가 한 Article에 연결됨!
    @JoinColumn(name = "article_id") // "article_id" 컬럼에 Article의 대표값을 저장함. ""는 코멘트의 FK로 선언됨. 즉, 이게 FK임.
    private Article article;
    //@조인컬럼 : 테이블에서 연결된 대상정보의 컬럼을 ""에 넣는다. name을 설정해줄수있고..

    @Column
    private String nickname;

    @Column
    private String body;


    /** CommentDto -> Entity */
    public static Comment createComment(CommentDto dto, Article article) {

        //예외처리 - 잘못된 dto의 id null여부 확인
        if(dto.getId() != null) //서버문제로 댓글중복갈겨질수있어서 그런것인가?
            throw new IllegalArgumentException("뎃글의 id가 없어야한다");     //이해못했다..이런상황..
        if(dto.getArticleId() != article.getId()) //(url이랑 json이랑 다르면)
            throw new IllegalArgumentException("게시글의 id가 없어야한다");

        //정상이면 엔티티생성및반환
        return new Comment(
                dto.getId(),
                article,    //애는 그냥 이미 article형으로 받아온거라 그대로간다. 어자피 위에보면 그렇게선언됨
                dto.getBody(),
                dto.getNickname()
        );
    }


    public void patch(CommentDto dto){  //여기가 Entity공간이니깐 Dto로 가져와서 여기서 변환하는게 맞다.

        //예외발생 - 아까처럼 json url다른경우에대한 처리
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글수정실패! 잘못된 id입력");
        }


        //예외없으면 객체를갱신
        //들어온값이 널이면  업데이트안하고 널이아닌 값이있으면 업데이트하겠단소리.
        if (dto.getNickname() != null) //입력된dto에 뭔가가 있을경우
            this.nickname = dto.getNickname(); //업데이트
        if (dto.getBody() != null) //입력된dto에 뭔가가 있을경우
            this.body = dto.getBody();         //업데이트
    }

}
