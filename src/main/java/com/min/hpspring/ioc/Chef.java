package com.min.hpspring.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {
    //세프는 식재료 공장을 알고있음
    private IngredientFactory ingredientFactory;

    //쉐프와 재료공장 협업을 위한 DI, 동작에대한 정보를 받아옴
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu){
    //요리재료준비
        Ingredient ingredient = ingredientFactory.get(menu);
        //재료들의 부모타입

        //Pork pork = new Pork("한돈등신");
        //Beef beef = new Beef("두부");

    //요리 반환
        return ingredient.getName() + "으로만든" + menu;
    }
}
