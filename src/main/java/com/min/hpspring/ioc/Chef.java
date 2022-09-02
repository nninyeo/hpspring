package com.min.hpspring.ioc;

public class Chef {
    public String cook(String menu){
        //요리재료준비
        //Pork pork = new Pork("한돈등신");
        Beef beef = new Beef("두부");
        //요리 반환
        return beef.getName() + "으로만든" + menu;




    }
}
