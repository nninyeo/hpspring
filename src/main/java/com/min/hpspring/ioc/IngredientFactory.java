package com.min.hpspring.ioc;

import org.springframework.stereotype.Component;

@Component//이걸 써서 해당 클래스의 객체를 만들고, IoC에 등록.
public class IngredientFactory {

    public Ingredient get(String menu) {
        switch (menu){
            case "돈가스":
                return new Pork("한돈등신");
            case "스테이크":
                return new Beef("두부");
            case "크리스피치킨":
                return new Chicken("국내산10호닭");
            default:
                return null;    //null은 좋은코드가아님.

        }
    }
}
