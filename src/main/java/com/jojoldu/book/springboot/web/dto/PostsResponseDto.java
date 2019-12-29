package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    현재 PostsSaveRequestDto클래스의 구조를 보면
    Entity클래스인 Posts클래스와 구조가 비슷하지만 DTO클래스를 추가로 생성했다.
    절대로 Entity클래스를 Request/Response 클래스로 사용해서는 안된다.

    Entity클래스는 데이터베이스와 맞닿은 핵심 클래스. Entity를 기준으로 테이블이 생성되고, 스키마가 변경
    Request와 Response용 DTO클래스는 VIEW를 위한 클래스라서 자주 변경이 필요한데 Entity클래스는 변경이 되면 안되므로
    사용해서는 안된다.

    View Layer와 DB Layer의 역할 분리를 철저하게 하는 것이 좋다.

    2019. 12. 29
    책에서는 test쪽에 소스가 적혀있는데 아닌것 같아서 옮긴다.

 */

@Getter
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
