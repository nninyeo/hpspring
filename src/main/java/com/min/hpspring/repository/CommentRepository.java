package com.min.hpspring.repository;

import com.min.hpspring.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//<>꺽쇠앞에는 관리대상(여기서는 코멘트 entity)를 넣고, 그 PK의 타입인 Long을 넣어주자
//이번엔 CrudRepo가 아니라 JpaRepo를 상속받자.
//JpaRepo는 Paging & Solting 기반으로 확장된 놈이다.
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //나중에 딴데서 쓸 기능들을 좀 정의해놓자

    //1. 우선 FindByArticleId를 만들어보자. - @Query를 이용해 만드는 방식!!!
    // 이 기능에서 articleID를 입력시 희망사항: List<Comment>반환
    //@Query: 특정쿼리를 실행할수있게 @사용. 여기다가 수행시킬 쿼리문을 넣어야함.
    @Query(value =
            "SELECT * " +
            "FROM comment " +
            "WHERE article_id = :articleId",//아래 기능정의한 메소드 파라미터 내용이랑 같아야함
            nativeQuery = true) //true로해야 직접쓴 SQL문이 작동하나봄. 정렬해준다.
    List<Comment> findByArticleId(Long articleId); //()안에 내용이 = : 이옆에 내용이랑 같아야함.
    // articleId를 찾지 못해 에러 발생 시, @Param 어노테이션으로 파라미터 정보 추가
    // List<Comment> findByArticleId(@Param("articleId") Long articleId);


    //2. 특정닉네임의 모든 댓글조회를 만들기 - findByNickname - XML로 만들거다!!!
    // 이 기능에선 입력값(String Nickname)으로 닉네임 넘겨주고 코멘트묶음을 반환받고싶다.

    List<Comment> findByNickname(String nickname);

    // resources/META-INF/orm.xml 내용:
//        <named-native-query name="Comment.findByNickname" result-class="com.min.hpspring.entity.Comment">
//            <query>   <![CDATA[  SELECT * FROM comment WHERE nickname = :nickname  ]]>    </query>
//        </named-native-query>


    //유니카 4미리로 5미리. 3미리가 염병임. 4미리 너무비싼데.. 1.5배가격.
    //4미리로 만든건 이유가있을거같긴함. 근데 잡아주는 장력이 1.5배
    //애꾸같이달았을때 안정성면에서 낫다는거지. 아아 어렵군..  일단 4로 가보고 힘들수도있으니깐. 그담에 5로 가는걸로!

}
