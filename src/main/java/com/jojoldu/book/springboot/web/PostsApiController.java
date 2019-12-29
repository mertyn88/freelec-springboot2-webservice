package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
    Controller와 Service에서 @Autowired가 없는데 스프링에서 Bean을 주입받는 방식은 다음과 같다
    - @Autowired
    - setter
    - 생성자

    이 중 가장 권장하는 방식이 생성자로 주입받는 방식(@Autowired는 권장하지 않음)
    생성자는 롬복인 @RequiredArgsConstructor에서 해결한다. final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복에서 대신 생성
    생성자를 직접 쓰지 않고 롬복 어노테이션을 사용하는 이유
        해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위해
 */

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    /*
        UPDATE 이므로 PutMapping사용
     */
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }
}
