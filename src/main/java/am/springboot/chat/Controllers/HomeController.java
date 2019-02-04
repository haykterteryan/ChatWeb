package am.springboot.chat.Controllers;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.MessageDto;
import am.springboot.chat.DTO.SearchUserDto;
import am.springboot.chat.Dao.MessageDao;
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
    MessageDao messageDao;

    public HomeController(UsersDao usersDao, MessageDao messageDao) {
        this.usersDao = usersDao;
        this.messageDao = messageDao;
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
       // modelAndView.addObject("friendsLists",friendsLists);
        return  modelAndView;

    }

    @GetMapping()
    public ModelAndView homepage(){
        getUserId();
        ModelAndView modelAndView = new ModelAndView("index");
        List<FriendsList> friendsLists = usersDao.getFriendsList(loggedInUserId);
        modelAndView.addObject("friendsLists", friendsLists);
        return modelAndView;

    }

    @GetMapping(value = "{id}")
    public ModelAndView messageHistory(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("index");
        List<MessageDto> messageDtos = messageDao.getMessageHistory(loggedInUserId,id);




        return modelAndView;
    }


}
