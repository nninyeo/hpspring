package com.min.hpspring.ioc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChefTest {
    @Test
    void 돈가스_요리하기() {
    //준비
        IngredientFactory ingredientFactory = new IngredientFactory();
        Chef chef = new Chef();
        String menu = "돈가스";
    //수행
        String food = chef.cook(menu);
    //예상
        String expected = "한돈등신으로만든돈가스";
    //검증
        assertEquals(expected, food);
        System.out.println(food);
    }
    @Test
    void 스테이크_요리하기(){
    //준비
        Chef chef = new Chef();
        String menu = "스테이크";
    //수행
        String food = chef.cook(menu);
    //예상
        String expected = "두부으로만든스테이크";
    //검증
        assertEquals(expected, food);
        System.out.println(food);
    }

}