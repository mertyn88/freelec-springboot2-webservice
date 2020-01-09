package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/*
    of()
        OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환한다.

    toEntity()
        User엔티티를 생성한다.
        OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때임
        가입할 때의 기본 권한을 GUEST로 주기 위해서 role 빌더값에는 Role.GUEST를 사용
 */

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private  String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes
                            , String nameAttributeKey
                            , String name
                            , String email
                            , String picture
                          ) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes ){
        System.out.println("3번째 수행 클래스 : 2번수행클래스의 로그인정보를 entity클래스에 적재하기 위한 클래스");

        if(registrationId.equals("naver")){
            //Naver
            return ofNaver("id", attributes);
        }else{
            //Google
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    //Naver 관련 Builder
    private  static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image")) //책에서는 값을 profileImage로 가져오는데 실제 맵을 열어보면 profile_image로 되어있다.
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //Google 관련 Builder
    private  static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
