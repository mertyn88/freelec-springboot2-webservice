package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/*
      registrationId
        현재 로그인 진행 중인 서비스를 구분하는 코드
        지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용

      userNameAttributeName
        OAuth2 로그인 진행 시 키가 되는 필드값을 말한다. Primary Key와 같은 의미

      OAuthAttributes
        OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용

      SessionUser
        세션에 사용자 정보를 저장하기 위한 DTO 클래스
        직접적인 User클래스를 사용하지 않고 SessionUser클래스를 생성한 이유는 예전처럼 Entity클래스이기 때문이다.
        그리고 현재 상태에서 그대로 사용하면 faild to convnert from type [java.lang.Object] 에러 발생
        User 클래스에 직렬화를 구현하지 않아서
        Entity 클래스는 언제 다른 Entity클래스와 관계가 형성될지 모르므로 함부로 DTO로 사용하지 않는다.
 */

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        System.out.println("2번째 수행 클래스 : 로그인버튼 클릭시 해당 함수로 오게 된다..");

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                                        Collections.singleton( new SimpleGrantedAuthority(user.getRoleKey()))
                                        , attributes.getAttributes(),attributes.getNameAttributeKey()
                                    );
    }

    private User saveOrUpdate(OAuthAttributes attributes){

        System.out.println("4번째 수행 클래스 : 받아온 값의 email로 조회하여 insert 및 update를 수행한다.");

        User user = userRepository.findByEmail(attributes.getEmail()).map(
                                            entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                                        .orElse(attributes.toEntity());

        return  userRepository.save(user);
    }
}
