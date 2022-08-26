package com.min.hpspring.service;


import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


//@Slf4j 굳이필요?..
@Service    //서비스 객체를 스프링부트에 생성하는 어노테이션
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> index() {
        return articleRepository.findAll();
    }
















}
