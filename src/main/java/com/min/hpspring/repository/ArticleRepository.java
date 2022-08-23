package com.min.hpspring.repository;

import com.min.hpspring.entity.Article;
import org.springframework.data.repository.CrudRepository;

//인터페이스를 한 이유: repo(셔틀)를 직접 구현할수도있지만 JPA에서 제공하는걸 써보기위함. extends CrudRepository 상속받기.
//그럼 CRUD레파지토리 기능을 정의없이 그대로쓸수있음. findById() 같은걸 쓸수있다.
//추가로 < > 넣고 여기 <T, ID> = <관리대상:Article엔티티, 관리대상 엔티티에서 대표값의 타입(여기선id)>
public interface ArticleRepository extends CrudRepository<Article, Long> {
//내용은 없어도될듯하다 어자피 받아다 그대로쓰면되니깐..
    //근데 그럼 그냥 CrudRepo를 그냥 그대로쓰면되는거아님? 응 아니야.



//이번에 articleRepository.save(article)라고 save를 썻잖아.
//아래 양키형들 주석이랑 분석해보면.
    /*
     * <S extends T> S save(S entity);  T는 부모, S는 자식
     * >>> 제네릭, <? extends Number>일경우 ?는 임의의 obj로 설정. Number나 그 하위(Integer Double)만 가능
     * >>> 단 extends가 아닌 super일 경우는 '이상'으로 Number랑 obj만 가능
     * >>> 즉, ?는 아무거나고 extends T는 T이하인데..
     * List는 구체화(reified)타입. 즉 Reifiable type이란 컴파일시 정보가 지워지지 않는 타입이란거.
     * 결론: 모르겠다. articleRepository.save(article)을 했을때 ... (article)이 S엔트리?
     *
     * https://thecodinglog.github.io/java/2020/12/09/java-generic-class.html
     * 요점은 즉, obj에 string넣고 int로 빼서써도 코드/컴파일오류는 안난나서 위험하다는거.
     * 그럼 그냥 한 형태만쓰면되는데 이거저거다하면 코드중복이 많아서 제네릭을쓴다.
     * <T>라는건 Type파라미터인데 뭐든들어갈수있고 T자리에 형을 넣어서 객체생성해서 쓴다.
     * (인자로 String같은 집어넣은 형을 넣고 리턴도 그걸로받는단거, 즉 규격을 같이보낸다는거)
     * 근데 두 형간에 연산을하든 뭘 할때 내부에서 형변환애에 문제가있나봄.
     * 타입파라미터로 불특정 다수 받으려는데 제약을 좀 걸고싶다 하면 쓰는게
     * 경계가 있는 파라미터 타입이다. T를 받을수있고 number을 상속받은 하위타입만 받을수있다.
     *
     *
     * Saves all given entities.
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     *         as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
     *           {@literal null}.
     */


}
