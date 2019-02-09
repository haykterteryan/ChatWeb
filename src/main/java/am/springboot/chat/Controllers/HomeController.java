package am.springboot.chat.Controllers;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.MessageDto;
import am.springboot.chat.DTO.RequestDto;
import am.springboot.chat.DTO.UserDto;
import am.springboot.chat.Dao.FriendRequestDao;
import am.springboot.chat.Dao.MessageDao;
import am.springboot.chat.Dao.UsersDao;
import am.springboot.chat.domain.FriendRequest;
import am.springboot.chat.domain.RequestAnswer;
import am.springboot.chat.domain.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {

    private List<FriendsList> friendsLists;
    private UsersDao usersDao;
    private int loggedInUserId;
    private MessageDao messageDao;
    private FriendRequestDao friendRequestDao;

    public HomeController(UsersDao usersDao, MessageDao messageDao,FriendRequestDao friendRequestDao) {
        this.usersDao = usersDao;
        this.messageDao = messageDao;
        this.friendRequestDao =friendRequestDao;
    }

    private  void getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            loggedInUserId = ((UserDomain) principal).getUserId();
        }
        else{loggedInUserId = -1;}
    }

    @GetMapping(value="/", params = "search")
    public ModelAndView search(@RequestParam(name = "search") String name){
        getUserId();

        ModelAndView modelAndView = new ModelAndView("index");

        List<UserDto> userDtos = usersDao.loadUserByname(name,loggedInUserId);

        modelAndView.addObject("searchresult", userDtos);
        modelAndView.addObject("friendsLists",friendsLists);
        return  modelAndView;
    }

    @GetMapping()
    public ModelAndView homepage(){

        getUserId();

        List<RequestDto> requestDtos = friendRequestDao.getFriendRequest(loggedInUserId);
        List<UserDto> unreadMessages = usersDao.getUnreadMessages(loggedInUserId);
        friendsLists = usersDao.getFriendsList(loggedInUserId);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("friendsLists", friendsLists);
        modelAndView.addObject("unreadMessages",unreadMessages);

        return modelAndView;

    }

    @GetMapping(value = "{id}")
    public ModelAndView messageHistory(@PathVariable int id){

        getUserId();

        List<MessageDto> messageHistory = messageDao.getMessageHistory(loggedInUserId,id);
        friendsLists = usersDao.getFriendsList(loggedInUserId);
        messageDao.markUnreadMessagesAsReaded(id);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("friendsLists", friendsLists);
        modelAndView.addObject("messagehistory",messageHistory);
        modelAndView.addObject("loggedInUserId", loggedInUserId);
        modelAndView.addObject("friend",usersDao.getUserName(id));

        return modelAndView;
    }

    @PostMapping(value = "friendrequest", consumes = "application/json")
    public String sendFriendRequest(@RequestBody FriendRequest friendRequest){
        friendRequestDao.sendFriendRequest(loggedInUserId,friendRequest.getUserId());
        return null;
    }

    @PostMapping(value = "request", consumes = "application/json")
    public ResponseEntity<?> request(@RequestBody RequestAnswer requestAnswer){
        getUserId();
        friendRequestDao.acceptOrDeniedFriendRequest(requestAnswer.getIsAccept(),loggedInUserId,requestAnswer.getUserId());
        return ResponseEntity.ok().build();
    }


}
