package com.min.hpspring.controller;

import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //컨트롤러니깐 그냥 달음
public class ArticleController {

    private ArticleRepository articleRepository;    //급한대로 에러막게 객체하나만듬.....

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
        //리턴에다가 뷰 페이지를 던져줌. articles폴더의 new.mustache를 찾아감.
    }

    @PostMapping("/articles/create")
    public String newArticleForm(ArticleForm form){ //파라미터로 DTO인 아티클폼 형의 form데이터를담아 html로부터 받아왔다.
        System.out.println(form.toString());

        // 컨트롤러가 dto를 Entity로 변환해서 repo에 태워 DB로 보내는 과정이다
        //1. dto -> entity
        Article article = form.toEntity();  //Article == (Entity형의)ArticleForm.

        //2. repository가 Entity를 DB로 저장하게하고 처리결과를 saved로 받아온다.
        Article saved = articleRepository.save(article);    //save할것은 entity화된 article객체다.




        return "";
    }

}
