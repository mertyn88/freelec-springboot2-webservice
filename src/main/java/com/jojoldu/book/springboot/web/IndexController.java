package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
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


    /*
        기존방식에서 어노테이션 방식으로 변경되면서 바뀐부분 체크

        //세션을 사용하기 위해서 추가하는 클래스
        private final HttpSession httpSession;

        삭제
        ---------------------------------------

        public String index(Model model){

        인자값 1개에서 2개로 변경 (Model model, @Loginuser SessionUser user)
        ---------------------------------------

        //책에서는 (User)로 형변환 하는데 말도 안됨  (SessionUser)로 해야함
        //CustomOAuth2UserService에서 로그인 성공 시 세션값을 SerssionUser저장
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        user를 가져오는  부분을 파라미터값으로 받아온다.
        ---------------------------------------

        해당 방식을 사용하면 어느 컨트롤러든지 @LoginUser만 사용하면 세션정보를 가져올 수 있게 된다.
     */


    @GetMapping("/")
      public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

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
