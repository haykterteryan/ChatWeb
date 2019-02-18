package am.springboot.chat.controller;


import am.springboot.chat.service.UserService;
import am.springboot.chat.domain.RegisterRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping
public class LoginController {

    private final UserService userService;



    public LoginController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/" ;
        }

        return "login";
    }


    @GetMapping("/register")
    public String register(RegisterRequest registerRequest){
        return "register";
    }

    @PostMapping("/register")
    public String regsisterPost(@Valid  @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "register";
        }
        userService.register(registerRequest);
        return "redirect:login";
    }


}
