package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
    @RunWith(SpringRunner.class)
    테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
    여기서는 SpringRunner라는 스프링 실행자를 사용
    즉 스프링부트 테스트와 JUnit 사이에 연결자 역할

    @WebMvcTest
    여러 스프링 테스트 어노테이션 중, Web(Strping MVC)에 집중할 수 있는 어노테이션
    선언할 경우, @Controller, @ControllerAdvice 등을 사용할 수 있다.
    단, @Service, @Component, @Repository는 사용 불가
    여기서는 컨트롤러만 사용하므로 선언

    @Autowired
    스프링이 관리하는 Bean을 주입 받는다.

    private MockMvc mvc
    웹API를 테스트할때 사용한다.
    스프링 MVC테스트의 시작점
    이클래스를 통해서 HTTP GET, POST 등에 대한 API를 테스트 할 수 있다.

    mvc.perform(get("/hello"))
    MockMvc를 통해 /hello 주소로 HTTp GET 요청을 한다.
    체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능

    andExpect(status().isOk())
    mvc.perform의 결과를 검증한다.
    HTTP Header의 Status를 검증한다.
    우리가 흔히 알고 있는 200, 404, 500등의 상태를 검증합니다.
    여기선 OK, 즉 200에러만을 확인한다.

    andExcept(content().string(hello))
    mvc.perform의 결과를 검증한다.
    응답 본문의 내용을 검증한다.
    Controller에서 "hello"를 리턴하기 때문에 이값이 맞는지 검증한다.

    param
        API테스트할 때 사용될 요청 파라미터를 설정한다.
        단 값은 String만 허용
        그래서 숫자/날짜등의 데이터를 등록할경우에는 문자열로 변경
    jsonPath
        JSON 응답값을 필드별로 검증할 수 있는 메소드
        $를 기준으로 필드명을 명시
        여기서는 name과 amount이니 $.name, $.amount로 검증
 */

@RunWith(SpringRunner.class)
//2020. 01. 09 추가 - SecurityConfig 클래스를 스캔대상에서 제외
@WebMvcTest(controllers = HelloController.class
            , excludeFilters =
                                {
                                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
                                })
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello가_리턴된다() throws  Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    /*
        실제 URL테스트 값
        http://localhost:8080/hello/dto?name=hello&amount=1000

        실제 JSON 결과값
        {"name":"hello","amount":1000}
     */
    @Test
    @WithMockUser(roles = "USER")
    public void HelloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                    get("/hello/dto").param("name",name).param("amount",String.valueOf(amount)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect(jsonPath("$.amount", is(amount)));
    }

}
