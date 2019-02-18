package am.springboot.chat.controller;

import am.springboot.chat.dto.FriendDto;
import am.springboot.chat.dto.MessageDto;
import am.springboot.chat.dto.RequestDto;
import am.springboot.chat.dto.UserDto;
import am.springboot.chat.dao.FriendRequestDao;
import am.springboot.chat.dao.MessageDao;
import am.springboot.chat.domain.FriendRequest;
import am.springboot.chat.domain.RequestAnswer;
import am.springboot.chat.domain.UserDomain;
import am.springboot.chat.service.FriendRequestService;
import am.springboot.chat.service.MessageService;
import am.springboot.chat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {

    private List<FriendDto> friendDtos;
    private final UserService userService;
    private final MessageService messageService;
    private final FriendRequestService friendRequestService;
    private int loggedInUserId;
    private final MessageDao messageDao;
    private final FriendRequestDao friendRequestDao;

    public HomeController(UserService userService, MessageService messageService,
                          FriendRequestService friendRequestService, MessageDao messageDao,
                          FriendRequestDao friendRequestDao) {
        this.userService = userService;
        this.messageService = messageService;
        this.friendRequestService = friendRequestService;
        this.messageDao = messageDao;
        this.friendRequestDao = friendRequestDao;
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

        friendDtos = userService.getFriendsList(loggedInUserId);
        List<UserDto> userDtos = userService.loadUserByname(name,loggedInUserId);

        modelAndView.addObject("searchresult", userDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        return  modelAndView;
    }

    @GetMapping()
    public ModelAndView homepage(){

        getUserId();

        List<RequestDto> requestDtos = friendRequestService.getFriendRequest(loggedInUserId);
        List<UserDto> unreadMessages = userService.getUnreadMessages(loggedInUserId);
        friendDtos = userService.getFriendsList(loggedInUserId);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        modelAndView.addObject("unreadMessages",unreadMessages);

        return modelAndView;

    }

    @GetMapping(value = "{id}")
    public ModelAndView messageHistory(@PathVariable int id){

        getUserId();

        if(!(friendRequestService.checkFriendship(loggedInUserId,id))){
        return new ModelAndView("redirect:/");
        }
        messageDao.markUnreadMessagesAsReaded(loggedInUserId,id);
        List<UserDto> unreadMessages = userService.getUnreadMessages(loggedInUserId);
        List<RequestDto> requestDtos = friendRequestService.getFriendRequest(loggedInUserId);
        List<MessageDto> messageHistory = messageService.getMessageHistory(loggedInUserId,id);
        friendDtos = userService.getFriendsList(loggedInUserId);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        modelAndView.addObject("messagehistory",messageHistory);
        modelAndView.addObject("loggedInUserId", loggedInUserId);
        modelAndView.addObject("friend",userService.getUserName(id));
        modelAndView.addObject("unreadMessages",unreadMessages);

        return modelAndView;
    }

    @PostMapping(value = "friendrequest", consumes = "application/json")
    public ResponseEntity sendFriendRequest(@RequestBody FriendRequest friendRequest){
        friendRequestDao.sendFriendRequest(loggedInUserId,friendRequest.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "request", consumes = "application/json")
    public ResponseEntity<?> request(@RequestBody RequestAnswer requestAnswer){
        getUserId();
        friendRequestDao.acceptOrDeniedFriendRequest(requestAnswer.getIsAccept(),loggedInUserId,requestAnswer.getUserId());
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "markAsReaded",consumes = "text/plain")
    public ResponseEntity markAsReaded(@RequestBody String personId){
        messageDao.markUnreadMessagesAsReaded(loggedInUserId,Integer.parseInt(personId));
        return ResponseEntity.ok().build();
    }
}
