package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
    기존의 TEST는 @WebMbcTest를 사용하였다. 하지만 본 테스트는 @WebMvcTest의 경우 JPA기능이 작동하지 않기 때문이다.
    Controller와 ControllerAdvice 등 외부 연동과 관련된 부분만 할성화 되니 지금같이 JPA 기능까지 한번에 테스트 할경우에는
    @SpringBootTest와 TestRestTemplate를 사용한다.

    해당 테스트를 진행할때 에러가 난다.
    PostsApiContoller.java를 보면 책에서는 @PutMapping을 사용하고 있는데 실제로는 Long.class에대한 에러가 발생한다.
    이슈 해결 URL : https://github.com/jojoldu/freelec-springboot2-webservice/issues/101
    책에서 오타가 나서 PostMapping을 PutMapping으로 작성이 되어서 나온거 같다.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("author")
                                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";


        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception{

        //given
        //1. 테스트 데이터 삽입
        Posts savedPosts = postsRepository.save(Posts.builder().title("타이틀").content("컨텐트").author("작성자").build());

        //2. 업데이트 하려는 ID값 가져오기
        Long updateId = savedPosts.getId();

        //3. 변경하려는 타이틀과 컨텐츠 설정
        String expectedTitle = "변경되는타이틀";
        String expectedContent = "변경되는컨텐트";

        //4. Update하려는 내용은 해당 DTO에 설정 ( 아직 업데이트 반영안됨)
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        //5. 수행 URL설정
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //6. 담아둔 DTO를 웹에 관련된 Entity에 담아두는것 같음 .. 추측..
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<PostsUpdateRequestDto>(requestDto);

        //when
        //7. 테스트 템플릿 변경 시작인가..
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        //8. 전체 셀렉트 (하지만 데이터 1건만 넣었으니 0번째 값만으로 판단)
        List<Posts> all = postsRepository.findAll();

        //9. 데이터 검증
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
}
