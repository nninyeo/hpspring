package com.min.hpspring.api;

import com.min.hpspring.dto.ArticleForm;
import com.min.hpspring.entity.Article;
import com.min.hpspring.repository.ArticleRepository;
import com.min.hpspring.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//   RestController에서는
//   불량코드를 보낼땐 ResponseEntity<Article>로 보내고???? 둘다인데??
//   정상코드를 보낼땐  body()에 싣어서 보내준다!??? 둘다인데?? ()가 널인지아닌지 차이아녀?

@Slf4j
@RestController //RestAPI용 컨트롤러! JSON 데이터 반환
public class ArticleApiController {
    //컨트롤러는 응답요청하고 리턴처리하는것만 집중

//   old
//    **이부분은 이제 서비스를 땡겨와서 쓸거라서 repo로 다이렉트가 아니라 중간업자인 서비스로 연결할거다.
//    @Autowired //외부꺼니깐 @로 땡겨와야함.DI를 가져와
//   private ArticleRepository articleRepository;//애가 정의가안되있어서 Field로 컨트롤러안에 생성해줌.

    @Autowired  //서비스 객체 땡겨오기.
    private ArticleService articleService;


    /** 전체 게시글 목록 조회  @GetMapping("api/articles") */
    @GetMapping("api/articles")
    public List<Article> index() {      //자료형은 그대로.

        return articleService.index();  //리턴은 서비스.index()가서 가져오라
    }


    /** 게시글 단건 조회  @GetMapping("api/article/{id}") */
    @GetMapping("api/article/{id}")
    public Article index(@PathVariable Long id){
        return articleService.show(id);
        //잘보면 리턴타입이 Article이다. 그전엔 리턴을 스트링으로 페이지를줬는데 애는 데이터자체를 던져버린다.
    }



    /** 게시글 작성: id빼고 사용바람  {"title": "asdf", "content": "vvv"} */
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        //반환형이 ResponseEntity<Article>인 이유는 반응결과랑 데이터 쏴줘야하니깐.
        //form대신 dto넣음. 그리고 그전엔 파라미터만받음되서 @PathVar 썻는데 json에선 그대로안받아지니 @ReqBody써야함.
        //클라이언트가 전송한 article을 받아와야하는데 이 형식이 없으니 articleForm form하면되는데
        //Article article = form.toEntity();
        //이번엔 이거를 dto라고 선언해주자. Form도 이름좀 바꾸고싶지만 일단그냥씀.

        Article created = articleService.create(dto);

        //ok시 잘 만들어진 데이터 created를 바디에 얹어보냄.
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created): //good
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//bad
    }



    /** 게시물 수정. url{id} 및 id가 잘 매치되야함. 실무에서 PATCH사용금지! 불안정. */
//    {"id": 1, "title": "수정할타이틀", "content": "수정할컨텐츠"}
//      만약 이렇게던지면 빈곳엔 리턴 null db도 null 들어감
//    {"id": 2, "title": "컨텐츠빼고수정할타이틀만보낸다"}
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,    //url id 따오기
                          @RequestBody ArticleForm dto) { //body로 받은 입력데이터
        //ResponseEntity<T>반환형: 이거 자바껀데 extends HttpEntity<T>를 해논거라 저렇게 사용해야한다.
        //Article로만 리턴하면 http정보가 리턴이안됨.
        //<Article>은 바디다. Type parameters: <T> – the body type 라고 써있네.


        Article updated = articleService.update(id, dto);


        //* ResponseEntity.status는 Controller의 역할임. 그 구분기준이 뭐지?

        //return update //api 리턴에다가 200번(updated만) 그냥보내도되지만
        //그래도 OK로 해서 body에다가 싣어보내보자.
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//*원래는 예외처리해야함.
    }



    /** 게시글 삭제  @DeleteMapping("/api/articles/{id}")  */
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){   //사용자입력body 필요없음

        Article deleted = articleService.delete(id);

        return (deleted != null) ?
        ResponseEntity.status(HttpStatus.OK).build():
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        //body(null) 줘도되고 build();줘도되고... http코드는 null보단 200이 낫고 그보다 OK가낫다
    }

    /** 트랜잭션 테스트 : POST로 여러글 동시생성 */
    //데이터형식: [{"title": "data", "content": "data"}{...}{...}...{...}]
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        //ResponseEntity에 Article을 던지는데 List로 묶어서 던질거다.
        //그 메소드 이름은 transactionTest라고 해줍시다.
        //이제 던져진 여러 데이터를 받아와야하니깐 (@RequestBody)를 통해 받아오는데
        //리스트로 묶은 ArticleForm을 받자. 변수명을 dtos 복수형으로 만들어봤다.

        //여긴 컨트롤러니깐 여기서 데이터받고 뭘 응답해줄지만 정하면됨. 자세한 순서와 내용은 서비스에서.

        List<Article> createdList = articleService.createArticles(dtos);
        //Service.createArticles()에 데이터 만들라고 dtos 던짐
        //잘 만들어지면  Article엔티티의 묶음형식으로 createdList에 넣어둔다.
        //별로면 bad주고 빌드해서 보내줌.
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


}//ArticleApiController.java
//==========================================================================================


//old
//    @GetMapping("api/articles")
//    public List<Article> index() {
//        return articleRepository.findAll();
//    }



//OLD
//    @GetMapping("api/article/{id}")
//    public Article index(@PathVariable Long id){ //오잉 index()겹치는데 파라미터 리턴 다르다고 되는건가..이런..
//        return articleRepository.findById(id).orElse(null);
//    }
//    //*patch delete는 잘 안씀. 신동혜말로는.. get post put을 주로씀. put은 누가봐도 업데이트니깐



//old
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto){
//        //form대신 dto넣음. 그리고 그전엔 파라미터만받음되서 @PathVar 썻는데 json에선 그대로안받아지니 @ReqBody써야함.
//
//        //클라이언트가 전송한 article을 받아와야하는데 이 형식이 없으니 articleForm form하면되는데
//        //Article article = form.toEntity();
//        //이번엔 이거를 dto라고 선언해주자. Form도 이름좀 바꾸고싶지만 일단그냥씀.
//        Article article = dto.toEntity();
//
//        return articleService.save(article);// save(article)을 DB로 저장해주면 되는거지.
//    }




//old
//    //Patch임.
////이렇게 던지시오. 요청url의 {id}도 잘 매치되야함.
////    {
////        "id": 1,
////        "title": "수정할타이틀",
////        "content": "수정할컨텐츠"
////    }
////만약 이렇게던지면 빈곳엔 리턴도널 db도 널들어감
////    {
////        "id": 2,
////        "title": "컨텐츠빼고수정할타이틀만보낸다"
////    }
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id,    //url id 따오기
//                          @RequestBody ArticleForm dto) { //body로 받은 입력데이터
//        //ResponseEntity<T>반환형: 이거 자바껀데 extends HttpEntity<T>를 해논거라 저렇게 사용해야한다.
//        //Article로만 리턴하면 http정보가 리턴이안됨.
//        //<Article>은 바디다. Type parameters: <T> – the body type 라고 써있네.
//
//
//        //1. 수정용 엔티티 생성
//        Article article = dto.toEntity();   // post로 받아온 dto -> Entity.
//        log.info("(((article))) id: {}, article: {}", id, article.toString());    //이 로그찍는거 {} 잘 기억해두기
//
//        //2. 대상 엔티티를 조회.
//        //근데 받아온건 article에있는데 왜 repo에다가 저렇게하지? 왜냐면 id를 받아왔으니깐 id로 게시글유무확인해서 target으로
//        //articleRepository애는 암만봐도 그냥 형식이야. IF맞네.
//        Article target = articleRepository.findById(id).orElse(null);
//
//        //3. 잘못된 요청처리 (대상이 없거나 id다를때)
//        if (target == null || id != article.getId()) { //null or id와 dto의 id가 다른경우
//            //400 잘못된 응답요청
//            log.info("잘못된 요청! id: {}, article: {}", id, article, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//바디는 그냥 null로 보내볼게요
//        }
//
//
//        /** 4. 업데이트 및 정상응답(200)  */
//        target.patch(article);//근데 작동이안된다???????? 왜안돼지???????
//
//// https://www.inflearn.com/questions/93071
////그런데 저희가 ORM을 쓴다거나 요청 mapping에서 DTO라는 객체를 받게 되면, user_name에 해당되는 field가 null로 적용이 됩니다.
//// 이는 kotlin, java등 여러 언어에서 parsing할때 나오는 현상입니다.
//
////GET을 통해서 정보를 받아오고 PUT으로 정보를 수정함에 있어서 GET에서 받은 부분 중 원본+수정본을
//// 같이 보냄으로써 코드의 유연함, 어떠한 field의 수정이 일어났는지 굳이 서버에서 관리를 하지 않을수 있기에
//// 굳이 PATCH보다는 PUT을 사용한다고 할 수 있습니다.
//        //결국 PATCH가 가질 수 있는 불안정성 때문에 실무에서는 사용을 꺼리는 것이군요.
//
//
//        // 1번꺼(post로 받아온 dto->entity) 를 저장파라미터로 넣음. 결과는 updated에 넣어두자
//        Article updated = articleRepository.save(article);
//
//        //return update //api 리턴에다가 200번(updated만) 그냥보내도되지만
//        //그래도 OK로 해서 body에다가 싣어보내보자.
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//
//    }










