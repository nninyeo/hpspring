package com.min.hpspring.repository;

import com.min.hpspring.entity.Article;
import org.springframework.data.repository.CrudRepository;

//인터페이스를 한 이유: repo(셔틀)를 직접 구현할수도있지만 JPA에서 제공하는걸 써보기위함. extends CrudRepository 상속받기.
//그럼 CRUD레파지토리 기능을 정의없이 그대로쓸수있음. findById() 같은걸 쓸수있다.
//추가로 < > 넣고 여기 <T, ID> = <관리대상:Article엔티티, 관리대상 엔티티에서 대표값의 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {



}
