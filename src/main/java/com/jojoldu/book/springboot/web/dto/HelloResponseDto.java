package com.jojoldu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
    @Getter
        선언된 모든 필드의 get메소드를 생성한다.
    @RequiredArgsConstructor
        선언된 모든 final필드가 포함된 생성자를 생성한다.
        final이 없는 필드는 생성자에 포함되지 않는다.


 */



@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
