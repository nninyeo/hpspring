package com.min.hpspring.service;

import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스는 부트와 연동되어 테스트된다는 뜻
class ArticleServiceTest {

    @Autowired ArticleService articleService;   //DI해와 아티클서비스 객체를 땡겨옴

    /** 테스트 - 전체 게시글 목록 조회  @GetMapping("api/articles") */
    @Test
    void 전체_게시글_목록_조회() {

    //예상
        //data.sql에 보면 데이터 있음, 그거보면 뭐가나올지 알만함.
        //1L 리터 뭐냐면... 정수형으로 출력하려고 끝에 명시적으로 L을 붙임. 리터럴이겠지
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        //애네 리스트에다 넣어버려.. Arrays.asList는 abc쳐박는거
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));


    //실제
        List<Article> articles = articleService.index();
        //articleService가 .index()를 호출하면 articles로 반환함.. List<Article>형태로
        //컨트롤러에서 내는 명령과 똑같다 사실상.

    //비교
        //asserEquals(예상데이터, 실제데이터)
        assertEquals(expected.toString(), articles.toString());



    }

    /** 게시글 단건 조회  @GetMapping("api/article/{id}") */
    @Test
    void 게시글_단건_조회_존재하는_id_입력하기() {

        Long id = -1L;

    //예상
        //Article expected = new Article(id, "가가가가", "1111");
        Article expected = null;
    //실제
        Article real = articleService.show(id);
        //id없는경우 orElse(null)이니 널이다.

    //비교
        //null일때는 toString()빼고하기.
        assertEquals(expected, real);
    }

    /** 게시글 작성  @PostMapping("/api/articles")  */
    @Test
    @Transactional //이걸로 글작성후 뒤에테스트 문제안생기게 롤백처리. 생성.변경.삭제같은건 다 해야함
    void 게시글_하나_정상작성하기() {


    //컨텐츠 준비
        Long id = 4L; //5쓰면안되고 Long은 리터붙여야하네
        String title = "러시아";
        String content = "4444";


        //dto데이터형태도 하나 만들어줘서 실제객체에 넣어봐야지 - 성공용
        //ArticleForm dto = new ArticleForm(null, title, content);
        //dto데이터형태도 하나 만들어줘서 실제객체에 넣어봐야지 - 실패용
        ArticleForm dto = new ArticleForm(id, title, content);


    //예상 - 성공가정: title과 content만 있는 dto를 입력한걸 성공이라 가정함.
    //    Article expected = new Article (id, title, content);
    //예상 - 실패가정: 실패시 널이 반환되니 넣을 넣자
        Article expected = null;

    //실제
        Article real = articleService.create(dto);

    //비교
        assertEquals(expected, real);
    }

    //실패시나리오는 id가 포함됬다던가 하면되겠지



}//*동시에 여러테스트돌리면 만약 create하는 메소드 등이 있으면 뒤에꺼 영향받음.
//그거 막고싶으면 @Test아래다가 @Transactional 쓰면됨.