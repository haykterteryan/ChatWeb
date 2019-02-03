package am.springboot.chat.Controllers;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.SearchUserDto;
import am.springboot.chat.Dao.UsersDao;
import am.springboot.chat.domain.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {


    UsersDao usersDao;
    static int loggedInUserId;
    List<FriendsList> friendsLists;


    public HomeController(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    private static void getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            loggedInUserId = ((UserDomain) principal).getUserId();
        }
        else{loggedInUserId = -1;}
    }

    @GetMapping(value="/", params = "search")
    public ModelAndView search(@RequestParam(name = "search") String name){
        ModelAndView modelAndView = new ModelAndView("index");
        List<SearchUserDto> searchUserDtos = usersDao.loadUserByname(name);
        modelAndView.addObject("searchresult",searchUserDtos);
        modelAndView.addObject("friendsLists",friendsLists);
        return  modelAndView;

    }

    @GetMapping()
    public ModelAndView homepage(){
        getUserId();
        ModelAndView modelAndView = new ModelAndView("index");
        friendsLists = usersDao.getFriendsList(loggedInUserId);
        modelAndView.addObject("friendsLists", friendsLists);
        return modelAndView;

    }

    @GetMapping(path = "{id}")
    public ModelAndView messageHistory(@PathVariable("{id}") int id){
        ModelAndView modelAndView = new ModelAndView();





        return modelAndView;
    }


}
