package com.jojoldu.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        //머스테치를 의존성에 추가한 덕분에 자동으로 return index의 앞의 경로는 src/main/resource/templates로
        //뒤의 확장자는 .mustache가 붙어서 자동으로 View Resolver가 처리하게 된다.
        return "index";
    }
}
