package com.min.hpspring.service;


import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j //굳이필요?..
@Service    //서비스 객체를 스프링부트에 생성하는 어노테이션
public class ArticleService {
    //서비스는 자기업무에 처리흐름과 트랜잭션을 관리한다.

    @Autowired
    private ArticleRepository articleRepository;




    /** 전체 게시글 목록 조회  @GetMapping("api/articles") */
    public List<Article> index() {

        return articleRepository.findAll();  //리턴은 repo가서 다 findAll 가져오라
    }





    /** 게시글 단건 조회  @GetMapping("api/article/{id}") */
    public Article show(Long id){ //오잉 index()겹치는데 파라미터 리턴 다르다고 되는건가..이런..
        return articleRepository.findById(id).orElse(null);
        //잘보면 리턴타입이 Article이다. 그전엔 리턴을 스트링으로 페이지를줬는데 애는 데이터자체를 던져버린다.
    }




    /** 게시글 작성  @PostMapping("/api/articles")  */
    public Article create(ArticleForm dto){
        Article article = dto.toEntity();
        if (article.getId() != null) //중복id에다가 create했을경우 수정되버려서 그거 막아버림.
            return null;
        return articleRepository.save(article);// save(article)을 DB로 저장해주면 되는거지.
    }



    /** 게시물 수정   @PatchMapping("/api/articles/{id}")  */
    public Article update(Long id,    //url id 따오기
                                          ArticleForm dto) { //body로 받은 입력데이터

        //1. 수정용 엔티티 생성
        Article article = dto.toEntity();   // post로 받아온 dto -> Entity.
        log.info("(((article))) id: {}, article: {}", id, article.toString());    //이 로그찍는거 {} 잘 기억해두기

        //2. 대상 엔티티를 조회.
        //근데 받아온건 article에있는데 왜 repo에다가 저렇게하지? 왜냐면 id를 받아왔으니깐 id로 게시글유무확인해서 target으로
        //articleRepository애는 암만봐도 그냥 형식이야. IF맞네.
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청처리 (대상이 없거나 id다를때)
        if (target == null || id != article.getId()) { //id가 없거나 다른경우
            //400 잘못된 응답요청
            log.info("잘못된 요청! id: {}, article: {}", id, article, article.toString());
            return null;
        }

        //4. 업데이트 - 문제없으면
        target.patch(article); //(patch()는 Article.java 내부 업데이트 메소드). 위에서 article은 entity화됨.
        Article updated = articleRepository.save(target);
        return updated;
    }



    /** 게시글 삭제  @DeleteMapping("/api/articles/{id}")  */
    public Article delete(Long id){

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            //400 잘못된 응답요청
            log.info("없는게시물");
            return null;
        }
        //articleRepository.deleteById(id);이거일줄알았지만
        articleRepository.delete(target);//이거였다니.. 둘다해보기.

        return target;
    }

    /** 트랜잭션 테스트  */
    @Transactional  //이 메소드를 트랙잭션으로 묶어줌. 트잭은 서비스가 관리함.
    //이 @가 없으면 실패시에도 DB에 데이터가 들어가버린다. 이게있어야 롤백해줌.
    public List<Article> createArticles(List<ArticleForm> dtos) {

        //1. dto묶음을 entity묶음으로 변환
        //dtos가 dto들 데이터들의 묶음이다.
        // 애를 스트림화시켜 맵화시켜서 하나하나 올때마다 toEntity를 수행해서 맵핑해주고
        //맵핑된걸 리스트로 변환함. 익숙하지않을수있는 문법
        //한줄로는 보기안좋음 = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());

        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        /* 위 3줄이랑 동일한 코드이다.
        List<Article> articleList = new ArrayList<>();
        for(int i = 0; i < dtos.size(); i++){
            ArticleForm dto = dtos.get(i);
            Article entity = dto.toEntity();
            articleList.add(entity);
        }                                               */


        //2. entity묶음을 DB로 저장
        //articleList 묶음을 또 stream으로 돌리며 하나하나꺼내진 article을 저장

        articleList.stream()
                .forEach((article -> articleRepository.save(article)));

        /* 위 2줄이랑 동일한 기능
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            articleRepository.save(article);        */

        //3. 강제 예외발생
        // repo에서 -1번값을 찾아라 이런거시켜보자. 일리갈이귀먼트엑셥션해놓고 결제실패라고 해둠
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")    //Rambda
        );

        //4. 결과반환
        return articleList;

    }
}
