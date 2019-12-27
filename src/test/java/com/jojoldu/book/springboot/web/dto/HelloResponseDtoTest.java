package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    /*
        assertThat
            assertj라는 테스트 검증 라이브러리 검증 메소드
            검증하고 싶은 대상을 메소드 인자로 받는다.
            메소드 체이닝 지원으로 isEqualTo같은 메소드도 같이 사용가능
        isEqualTo
            assertj의 동등 비교 메소드
            assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공

        JUnit과 비교하여 assertj의 장점
            CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않는다.
            자동완성이 좀더 확실하게 지원된다.

        ** 책에서의 그레이들 버전은 4.10인데 비해 현재 디폴트 그레이들 버전은 5.2버전이다.
           그래서 그런지 롬복이 제대로 먹지 않고 초기화 에러가 발생한다.
           그러므로 책에 대한 테스트를 동일하게 맞추기위해서는 4.10.2버전으로 다운그레이드 할 필요가 있다.
           이슈 해결 URL : https://github.com/jojoldu/freelec-springboot2-webservice/issues/2

        해당 경로의 gradlew를 실행하여 4.10.2로 변경하는 설정 명령어를 넣고 빌드 하면 4.10.2버전으로 다운그레이드된다.
        /home/leejunmyung/IdeaProjects/freelec-springboot2-webservice/gradlew wrapper --gradle-version 4.10.2

     */

    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
