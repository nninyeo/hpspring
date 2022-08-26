package com.min.hpspring.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //REST API, 일반컨트롤러는 뷰를 반환, REST는 데이터반환.
public class FirstApiController {



    //로컬호스트:포트/api/hello
    @GetMapping("/api/hello")
    public String hello(){
        return "helloworld";

    }

}
