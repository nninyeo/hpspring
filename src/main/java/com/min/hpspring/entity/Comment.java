package com.min.hpspring.entity;

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



}
