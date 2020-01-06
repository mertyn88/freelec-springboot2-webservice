package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    //머스테치와 OAuth2.0을 사용하기 위해서 추가하는 클래스
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());

        //책에서는 (User)로 형변환 하는데 말도 안됨  (SessionUser)로 해야함
        //CustomOAuth2UserService에서 로그인 성공 시 세션값을 SerssionUser저장
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        //값이 있을 경우에만 할당
        //값이 없으면 model에 userName이라는 값 자체가 없으므로 null이다.
        if(user == null){
        }else{
            model.addAttribute("userName", user.getName());
        }

        //머스테치를 의존성에 추가한 덕분에 자동으로 return index의 앞의 경로는 src/main/resource/templates로
        //뒤의 확장자는 .mustache가 붙어서 자동으로 View Resolver가 처리하게 된다.
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
