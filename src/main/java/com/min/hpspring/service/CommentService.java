package com.min.hpspring.service;

import com.min.hpspring.dto.CommentDto;
import com.min.hpspring.entity.Comment;
import com.min.hpspring.repository.ArticleRepository;
import com.min.hpspring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;



    /**
     * 댓글 목록 조회: GET - api/articles/{article_id}/comments
     *  구버전!!!!!
     */
    //코멘트리파지토리에서 id로 찾아오고 파라미터로 아이디 넣어주면 코멘트 묶음을 반환해줄거다.
    public List<CommentDto> comments_old(Long article_id) {

    /* 조회: 댓글목록 */
        List<Comment> comments = commentRepository.findByArticleId(article_id);
        //코멘트리스트형 코멘트들 = 코멘트리파짓에서.아이디로찾어(article_id로)

    /* 변환: 엔티티 -> DTO */
        //그릇만들기 - CommentDto형을 담는 리스트 변수인 dtos생성
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        //new로 dto담는 어레이리스트 만듬, 그걸로 dtos를 만들어서 거기에 다 담을거다.
        //아 이거어렵네 어렵네.. 이해안된다... https://yoon-dailylife.tistory.com/7

        //만든그릇에 넣기 - 하나하나 리스트에서 꺼내서 변환해서 넣을것이다.
        for (int i = 0; i < comments.size(); i++){

            //리스트에서 하나 꺼내기
            Comment c = comments.get(i);
            // List.java 파고들면 get(int index);이란게 있다.
            // 그형태의 comment들이니깐 거기 순서붙이면 리턴해주는게 애다. 리턴받아 잠깐 c에넣어두자
            // 이 c에는 코멘트형 코멘트 한줄이 들어가있을거다.

            //하나 꺼낸거 createCommentDto()를 이용해 변환해서 CommentDto형 그릇에 하나 넣기
            CommentDto dto = CommentDto.createCommentDto(c);
            //createCommentDto는 클래스메소드임. 그래서 앞에 클래스명.클래스메소드 라고해줌.
            // 코멘트Dto형 dto에 각 c를 집어넣도록 하자.  createCommentDto는 클래스메소드.
            // .createCommentDto(); 라는 이름의 생성메소드가 있다. 역할은..

            //변환된거 그릇리스트에다가 옮기기
            dtos.add(dto);
            //추가했다
        }
        return dtos;
    }



    /**
     * 댓글 목록 조회: GET - api/articles/{article_id}/comments
     *  신버전!!!!!
     */
    //코멘트리파지토리에서 id로 찾아오고 파라미터로 아이디 넣어주면 코멘트 묶음을 반환해줄거다.
    public List<CommentDto> comments(Long articleId) {


        /* 변환: 엔티티 -> DTO */
        return commentRepository.findByArticleId(articleId)//코멘트리스트반환(조회)
                .stream()//(조회된)데이터들을 하나하나꺼내서
                .map(comment -> CommentDto.createCommentDto(comment))//맵핑해줌.
                //첫comm: 들어가는애들 -> 변환기(클래스.클래스메소드) 둘째comm: 입력
                .collect(Collectors.toList());
                //애들이 반환하는건 Steam의 오브젝트인데, .collect가 묶어준다. 앤 JAVA.util.stream소속.

    }






    /** 댓글 생성  */




    /** 댓글 수정 */




    /** 댓글 삭제 */




}//CommentService
