package am.springboot.chat.Controllers;


import am.springboot.chat.Servise.UserService;
import am.springboot.chat.domain.RegisterRequest;
import am.springboot.chat.domain.UserDomain;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

            Object principal = authentication.getPrincipal();
            int id;
            if(principal instanceof User){
                id = ((UserDomain) principal).getUserId();
            }
            else{id = -1;}

            return "redirect:/" ;
        }

        return "login";
    }


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String regsisterPost(@ModelAttribute RegisterRequest registerRequest){
        userService.register(registerRequest);
        return "redirect:login";
    }


}
