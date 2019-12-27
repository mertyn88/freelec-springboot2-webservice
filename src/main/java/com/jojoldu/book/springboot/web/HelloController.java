package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    @RestController
    컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
    예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할수 있게 해준다.

    @GetMapping
    HTTP Method인 Get의 요청을 받을 수 있는 API로 만들어준다.
    예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용되었다.
    /hello으로 요청이 오면 문자열 hello를 반환하는 기능

    @RequestParam
        외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
        여기에서는 외부에서 name이란 이름으로 넘긴 파라미터를 메소드 파라미터 name에 저장
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //Page 75
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name,amount);
    }

}
