package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    /*
    insert into posts(author, content, title) values('이준명', '테스트내용', '테스트제목');

    //메모리로 디비가 실행되므로 시작할때마다 테스트 데이터를 넣어줘야한다.

    curl -XPOST -H "Content-Type: application/json" -d '
    {
        "author" : "이준명"
        ,"title" : "제목입니다."
        ,"content" : "내용입니다."
    }
    ' http://localhost:8080/api/v1/posts

     */

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
