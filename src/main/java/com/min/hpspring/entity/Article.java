package com.min.hpspring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


//- Entity: 자바객체를 DB가 이해할 수 있게 잘 규격화된 데이터. 미네랄
// - Repository: 이 레파지토리라는 일꾼을 통해 잘 전달되고 처리된다. = 셔틀 Repo. scv 미네랄들고 움직임
// 컨트롤러가 dto를 Entity로 변환해서 repo에 태워 DB로 보낸다.
// 여기서 테이블도 생성하나봄.

@Entity //DB가 해당 객체를 Entity객체라고 인식할 수 있게하는 @
@AllArgsConstructor //롬복, 생성자 생성해줌
@ToString //lombok, Tostring()생성
@NoArgsConstructor //디폴트생성자생성
@Getter
public class Article {

    //DB에서 관리하는 Table단위에 연결해주는 @Column작업
    @Id //고유id
    //@GeneratedValue //값자동생성. JAVA에서 컨트롤함.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//DB가 자동생성 컨트롤
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    //내용에 null이 올까봐 무서워서 패치기능을 만든답니다
    //근데 작동이 안되는디요???
    public void patch(Article article) {
        if (article.title != null)//널이면 업데이트안하고 널이아닌 값이있으면 업데이트하겠단소리.
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }


    //@Getter 없을때 필요한거.
//    public Long getId() {
//    }

//      @AllArgsConstructor 로 생성해버려서 노필요
//    public Article(Long id, String title, String content) { //우클Gen-Construct해서 만들어진거
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }


   // @ToString 와 중복. 노필요
//    @Override
//    public String toString() { //우클Gen-toString해서 만들어진거
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }



//@NoArgsConstructor 와 중복. 노필요
    //?????기본생성자가 없으면 어째 오류가남.아래것들 깡그리 없애면? 그럼오류남
//    //이게 파라미터가 아무도없는 디폴스생성자인데 가지고있어야하는걸 @NoArgsConstructor을 통해 없앤다.
//    public Article() {
//    }


    //이거 왜필요함? 하는게없음.
//    public Article toEntity() {
//        return new Article();
//    }
//





}
