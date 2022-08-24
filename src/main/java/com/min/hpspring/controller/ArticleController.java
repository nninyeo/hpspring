package com.min.hpspring.controller;

import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //컨트롤러니깐 그냥 달음
@Slf4j  //로그용. 시스템아웃은 성능이 구려서 대체재로 사용
public class ArticleController {

//표현(뷰)을 위한 데이터는 dto:        요리 :  API 규격은 dto로 설계
//로직과 저장을 위한 데이터는 entity: 식자재 : 실제 데이터는 entity로 관리
    @Autowired  //오토와이어드에 대해 설명해줄수있는가? intellij는 오토와이어드 사용자제하라함.
    private ArticleRepository articleRepository;

//    private CrudRepository crudRepository;  //test, 작동불가

    //CrudRepository를 상속받은 ArticleRepository IF를 쓰려면
    //급한대로 에러막게 객체하나만듬.....
    //이거 원래 객체를 만들고 = new ArticleRepository로 객체를 만들어줘야하는데.. impl로..
    //그걸 @Autowired가 알아서해준다. 부트가 미리 생성해둔 객체를 갖다가 자동으로 연결한다.
    //배송시키면 어디어떻게 물류센터거치는지 그런거 알필요없다는거..
    //추가적으로 CrudRepo인터페이스에 다양한 추상메서드가 존재함. JPA가 해당 IF의 구현체 클래스를 자동생성해줌. 그걸 @로 가져와쓴다.
    //근데 캐스팅은 언제해줬데?? crudRepository를 쓰면 앞에 (Article)형변환하라고뜨는데.. 심지어 null오류도남.
    //언제해줬냐면 IF상속받을때 <Article, Long> 넣었단다.

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
        //리턴에다가 뷰 페이지를 던져줌. articles폴더의 new.mustache를 찾아감. (.mustche는 생략)
    }

    @PostMapping("/articles/create")
    public String newArticleForm(ArticleForm form){ //파라미터로 DTO인 아티클폼 형의 form데이터를담아 html로부터 받아왔다.

        log.info(form.toString());

        // 컨트롤러가 dto를 Entity로 변환해서 repo에 태워 DB로 보내는 과정이다
        //1. dto -> entity
        Article article = form.toEntity();  //Article == (Entity형의)ArticleForm.

        //2. repository가 Entity를 DB로 저장하게하고 처리결과를 saved로 받아온다.
        //Article saved = (Article)crudRepository.save(article);//<Article, Long>을 넣을수없다.
        Article saved = articleRepository.save(article);    //save할것은 entity화된 article객체다.


        //log4j로 교체되었다. 사용법은 log.info()만 써주면 동일.
//        System.out.println(saved.toString());//log
         log.info(saved.toString());

        return "redirect:/articles/" + saved.getId(); //작성후갈곳 + 글번호 = 해당글
    }

    @GetMapping("/article/{id}")    //{id}는 변하는수.
    public String show(@PathVariable Long id, Model model){
        //@설명: id값을 컨트롤러한테 받아오겠다고함. 타입은 롱이고 변수하나만들고
        //model은 그냥 모델담는 그릇이다. 임포트항목임.

        log.info("id = " + id); //매우 sysout처럼 쓰면됨.


        //1. id로 데이터(Article 1줄)를 가져옴. Article타입의 article엔티티로 아이디를 찾아서 repo셔틀통해 받아와저장. (id).orelse(null):아이디값을 통해 찾았는데 그게 없으면 널을 반환해라
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //Article articleEntity = articleRepository.findById(id).orElse(null);   //optional<T>는 고급기술이다. 지양바람.

        //2. 가져온 데이터를 모델에 등록
        //모델.등록("바인딩명",바인딩해줄데이터그릇); "article"은 {{#article}}{{/}}에 바인딩.
        model.addAttribute("article", articleEntity);

        //3. 보여줄 페이지를 설정
        return "articles/show"; //articles/show.mustache
    }


    /**
     *  Entity는 셔틀용 가방. Repo는 셔틀. (셔틀의 방) 아니그럼 엔티티가 셔틀이고 Repo는 캐시아님?
     *
     *
     */
    @GetMapping("/articles")
    public String index(Model model){
        //article 엔티티 list를 뷰로 전달할땐 모델을 써야하므로 모델을 para로 추가. 그릇같은건가?


        //1. 모든 아티클 가져온다.
        //.findAll(): 해당 리파지토리에 있는걸 다 가져옴
        /*
         List<Article> articlesEntityList = articleRepository.findAll();에선 타입불일치
         그래서 = (List<Article>)articleRepository라고 형변환하던가 (익숙해서 이거쓸예정)
         리턴타입을 List<Article>에서 Iterable<Article>로 바꾸던가
         참고로 순서가,, IF Iterable > IF Collection > IF List > class ArrayList (말단)
           리스트 대신 어레이리스트 써도되는데 리스트가 상위니깐 그냥 상위로 ㅎ
        */

        List<Article> articlesEntityList = (List<Article>)articleRepository.findAll();


        //2. 가져온 아티클 묶음을 뷰로 전달한다.
        //model.추가하기("데이터바인딩명", 추가할거);
        model.addAttribute("articleList", articlesEntityList);

        //3. 뷰 페이지를 설정한다

        return "articles/index";    // articles/index.mustache
    }














}//end class