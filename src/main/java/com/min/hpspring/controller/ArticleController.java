package com.min.hpspring.controller;

import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //컨트롤러니깐 그냥 달음
public class ArticleController {

//표현(뷰)을 위한 데이터는 dto:        요리 :  API 규격은 dto로 설계
//로직과 저장을 위한 데이터는 entity: 식자재 : 실제 데이터는 entity로 관리
    @Autowired
    private ArticleRepository articleRepository;
    private CrudRepository crudRepository;  //test
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
        System.out.println(form.toString());

        // 컨트롤러가 dto를 Entity로 변환해서 repo에 태워 DB로 보내는 과정이다
        //1. dto -> entity
        Article article = form.toEntity();  //Article == (Entity형의)ArticleForm.

        //2. repository가 Entity를 DB로 저장하게하고 처리결과를 saved로 받아온다.
        //Article saved = (Article)crudRepository.save(article);//<Article, Long>을 넣을수없다.
        Article saved = articleRepository.save(article);    //save할것은 entity화된 article객체다.
        System.out.println(saved.toString());//log

        return "";
    }

}
