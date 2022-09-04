package com.min.hpspring.service;

import com.min.hpspring.dto.CommentDto;
import com.min.hpspring.entity.Article;
import com.min.hpspring.entity.Comment;
import com.min.hpspring.repository.ArticleRepository;
import com.min.hpspring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     *  리턴값: List<CommentDto>
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

//    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
//                                             @RequestBody CommentForm form) {
    @Transactional  //CUD에는 붙여야지 데이터변경생기니
    public CommentDto create(Long articleId, CommentDto dto) {
        //CommentForm form이 아닌건 폼이 아니라 json으로 날아오기때문. 그게 dto형


        //1. 게시글조회 및 예외발생.
        //여기서는 articleRepo에 접근해야지.
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글생성실패. 원래게시글이없음"));
        //댓글쓰는사이에 게시글이 지워질수있으니깐.

        //2. 댓글엔티티 생성 - 클메 만들어오기. dto -> Entity
        //나는 form dto을 엔티티화 라고는 했으나, 결국같은소리인가? 이거 생성하려면 또 static 클메 써야함.
        Comment comment = Comment.createComment(dto, article);


        //3. DB에 저장 - 댓글엔티티를 인자로.. 게시물id는 코멘트엔티티 안에 있어서 노상관.
        //.save()는 상속받은 JPA기능인데, IF가 애 리턴을 Comment형이라고 해놔서 리턴이 코멘트형이다.
        Comment created = commentRepository.save(comment);

        //4. DTO로 변경해서 반환. 여기서 또 클메사용.
        return CommentDto.createCommentDto(created);
    }



    /** 댓글 수정 */
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

    //1. 코멘트조회 및 예외발생.
        //여기서는 articleRepo에 접근해야지. comment라고 안하고 target으로한 이유는 patch()를 쓸거라그래
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글수정실패. 원래코멘트가없음"));
        //댓글수정하는 쓰는사이에 게시글이 지워질수있으니깐.

    //정상이면 수정해줌, target은 타겟잡아논 코멘트형 코멘트, dto는 덮어씌울거
        target.patch(dto);


    //수정한거 DB갱신 해야지, 컬럼JPA에서 알아서 고치지못함. repo를 통해 .save()해줘야함.
        Comment updated = commentRepository.save(target);//id들어가라고 찾아둔 target넣어줌


    //댓글 엔티티 DTO로 변환 및 반환
        //Repo는 탐색기인거고 데이터 가져오려면 Comment.java엔티티가서 에서 직접가져와야함.
        //즉, 엔티티에서 DTO로 꺼내오는게 필요한데 그게 Service (DTO)에 만들어져있다.
        //파라미터로 이 메서드가 받아온 dto에는 이미 고정된 comment의 id가 있으니 그대로써도됨.
        return CommentDto.createCommentDto(target);//(dto)는 변경전인것이 그대로있어서 리턴하면안댐.

    }



    /** 댓글 삭제 */
    public CommentDto delete(Long id) {

        //1. 코멘트조회 및 예외발생.
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글삭제실패. 원래코멘트가없음"));

        //2. 댓글 DB에서 삭제
        commentRepository.delete(target);
        //delete()는 반환없음

        //3. 삭제댓글을 DTO로 반환
        return CommentDto.createCommentDto(target); //지워진건 그냥 리턴해도됨.
    }







}//CommentService
