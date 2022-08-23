package com.min.hpspring.dto;

import com.min.hpspring.entity.Article;

public class ArticleForm {

    private String title;
    private String content;


    //우클릭 Generate - Constructor(생성자) - 두개선택 해서 자동생성된 메소드
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }   //여기까지는 기본적으로 프론트단 form데이터 받아오려면 갖추어야할 형태이다.

    @Override   //우클릭 Generate - toString() - 두개선택 해서 자동생성된 메소드. 역할: 로그보려는건가?
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

        //Article은 Entity클래스고 이 클래스에 객체를 생성해야하니까 아래 생성자를 호출해야한다. (다 private하시니까)
//    public Article toEntity(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

    //컨트롤러에서 엔티티화시켜달라고 날라온 form데이터.
   public Article toEntity() {  //Article형 데이터를 반환하는 toEntity메소드.

        //아래는 Article클래스의 새 객체를 생성해달라고 파라미터 넣어주는거다.
       // 따라가보면 this.id 이런 생성자들이 있다. 그기에 @Column등록하고 끝이다.
        return new Article(null, title, content);   //id는 널주고 content랑은 dto에 담긴걸 그대로줌. 이렇게 반환.
    }//즉,  dto에서는 ArticleForm()을 통해 받은 두 데이터를 가지고있다가 toEntity()가 데이터달라고 하면 그걸 넘겨준다.
}
