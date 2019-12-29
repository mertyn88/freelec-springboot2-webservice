package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /*
        2019. 12 .29
        UPDATE부분은 책에서 제대로 다루지 않은것같다. 다운받은 소스를 참고 하였다.
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. ID = "+id));

        /*
            update기능에서 데이터베이스에 쿼리를 날리는 부분이 없다. 이게 가능한 이유는 JPA의 영속성 컨텍스트 때문이다.
            영속성 컨텍스트란, 엔티티를 영구 저장하는 환경이다. 일종의 논리적 개념이며 JPA의 핵심 내용은 엔티티가 영속성
            컨텍스트에 포함되어 있는가 아닌가로 갈린다.
            JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션 활성화) 트랜잭션 안에서 데이터베이스의
            데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다.
            이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에서 해당 테이블에 변경분을 반영한다.
            즉, Entity 객체의 값만 변경하면 별도로 update쿼리를 날릴 필요가 없다. 이 개념을 더티 체킹(Dirty Checking)

            ....어렵다... Insert와 마찬가지로 로그상에는 Update쿼리가 잘 실행되어있긴하다.
         */
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /*
        2019. 12. 29
        책에서는 나오지 않아서 다운받은 소스 참고
     */
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. ID = "+id));

        return new PostsResponseDto(entity);
    }
}
