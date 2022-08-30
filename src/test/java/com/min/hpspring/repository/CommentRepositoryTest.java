package com.min.hpspring.repository;

import com.min.hpspring.entity.Article;
import com.min.hpspring.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest    //원래 @SpringBootTest인데 이거는 repo로 테스트하니깐.. JPA연동테스트이므로 @DataJpaTest를 사용
class CommentRepositoryTest {

    //자재준비..
    @Autowired CommentRepository commentRepository;
    //오토와이어드가 이렇게되는거라니

    @Test
    @DisplayName("글번호로코멘트리스트뽑기")
    void findByArticleId() {

        /* CASE 1 : 4번 글번호에 대한 리스트를 뽑아본다.*/
        {
        //- 입력데이터준비
            long articleId = 4L;
            //실제 게시글도 하나 있어야한다. 반환형식보니 아래같이 코멘트마다 원본글이 달려서
            //Comment(id=1, article=Article(id=4, title=당신의 인생 영화는?, content=댓글 ㄱ), nickname=Park, body=굳 윌 헌팅), Comment(id=2, article=Article(id=4, title=당신의 인생
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");

        //- 실제수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

        //- 예상 : 애네 3개. DB에있는대로써줌.
            //            INSERT INTO comment(id, article_id, nickname, body) VALUES(1, 4, 'Park', '굳 윌 헌팅');
            //            INSERT INTO comment(id, article_id, nickname, body) VALUES(2, 4, 'Kim', '아이 엠 샘');
            //            INSERT INTO comment(id, article_id, nickname, body) VALUES(3, 4, 'Choi', '쇼생크의 탈출');
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");

            List<Comment> expected = Arrays.asList(a, b, c);


        //- 검증
            assertEquals(expected.toString(), comments.toString(), "4번글의 모든 댓글출력함");
        }



        /* CASE 2 : 1번 글번호에 대한 리스트를 뽑아본다.*/
        {
            //- 입력데이터준비
            long articleId = 1L;
            //실제 게시글도 하나 있어야한다. 반환형식보니 아래같이 코멘트마다 원본글이 달려서
            //Comment(id=1, article=Article(id=4, title=당신의 인생 영화는?, content=댓글 ㄱ), nickname=Park, body=굳 윌 헌팅), Comment(id=2, article=Article(id=4, title=당신의 인생
            Article article = new Article(1L, "가가가가", "1111");

            //- 실제수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            //- 예상 : 리플이없음.
            List<Comment> expected = Arrays.asList();   //null이 아니라 이렇게 비워두기.

            //- 검증
            assertEquals(expected, comments, "1번글의 모든 댓글은 아마 없을것");
        }

        /* CASE 3 :  9번게시글의 모든댓글 테스트.*/

        /* CASE 4 : 9999번의…*/

        /* CASE 5 : -1번 게시글*/


    }

    @Test
    @DisplayName("닉네임으로코멘트리스트뽑기")
    void findByNickname() {

        {
        //- 입력데이터준비
            String testNickname = "Kim";
            Article article1 = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Article article2 = new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ");
            Article article3 = new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ");

        //- 실제수행
            List<Comment> comments = commentRepository.findByNickname(testNickname);


        //- 예상 - 아래3개
//            VALUES(2, 4, 'Kim', '아이 엠 샘');
//            VALUES(5, 5, 'Kim', '샤브샤브');
//            VALUES(8, 6, 'Kim', '유튜브');
            //형식: Comment aaa = new Comment(id, Article, nickname, body);
            //Article쪽 번호는 5L이런거쓰면 Article형이라고 안받아줘서 아예 article객체 만들어버림.
            Comment a = new Comment(2L, article1, "Kim", "아이 엠 샘");
            Comment b = new Comment(5L, article2, "Kim", "샤브샤브");
            Comment c = new Comment(8L, article3, "Kim", "유튜브");

            List<Comment> expected = Arrays.asList(a, b, c);


            //- 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의댓글들");

        }

        /* 다른 테스트케이스: 해보기
        null
        공백(””)
        “i”
         */

    }
}