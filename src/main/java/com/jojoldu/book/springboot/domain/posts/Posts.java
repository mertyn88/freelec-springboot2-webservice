package com.jojoldu.book.springboot.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
    @Entity
        테이블과 링크될 클래스임을 나타낸다.
        기본값으로 클래스의 카멜케이스 이름을 언더스크어 네이밍(_)으로 테이블 이름을 매칭합니다.
        ex) SalesManager.java -> sales_manager table

    @Id
        해당 테이블의 PK필드를 나타낸다.

    @GeneratedValue
        PK의 생성규칙
        스프링부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야함 auto_increment가 된다.

    @Column
        테이블의 컬럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 컬럼이 됨
        사용하는 이유는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
        문자열의 경우 VARCHAR(255)가 기본, 사이즈를 500으로 늘리거나 타입을 TEXT로 변경한다거나 할때


     [Lombok]
     @NoArgsConstructor
        기본생성자 자동 추가
     @Getter
        클래스내 모든 필드 Getter메소드 자동생성
     @Builder
        해당 클래스의 빌더 패턴 클래스를 생성
        생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함

     * 빌더와 생성자는 생성 시점에 값을 채워주는 역할은 같다.
       하지만 생성자의 경우 채워야 할 필드가 무엇인지 명확히 지정할 수가 없다.
 */

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /*
        책에서는 나와있지 않아서 다운받은 소스에서 보고 추가함
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
