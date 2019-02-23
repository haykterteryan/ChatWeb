package am.springboot.chat.controller;

import am.springboot.chat.dao.FriendsDao;
import am.springboot.chat.domain.BlockFriend;
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
    private final FriendsDao friendsDao;

    public HomeController(UserService userService, MessageService messageService,
                          FriendRequestService friendRequestService, MessageDao messageDao,
                          FriendRequestDao friendRequestDao, FriendsDao friendsDao) {
        this.userService = userService;
        this.messageService = messageService;
        this.friendRequestService = friendRequestService;
        this.messageDao = messageDao;
        this.friendRequestDao = friendRequestDao;
        this.friendsDao = friendsDao;
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

        UserDto currentUserDto =  userService.getUserName(loggedInUserId);
        List<RequestDto> requestDtos = friendRequestService.getFriendRequest(loggedInUserId);
        friendDtos = userService.getFriendsList(loggedInUserId);
        List<UserDto> userDtos = userService.loadUserByname(name,loggedInUserId);
        List<UserDto> unreadMessages = userService.getUnreadMessages(loggedInUserId);


        modelAndView.addObject("searchresult", userDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("unreadMessages",unreadMessages);
        modelAndView.addObject("userInfo",currentUserDto);


        return  modelAndView;
    }

    @GetMapping()
    public ModelAndView homepage(){

        getUserId();

        List<RequestDto> requestDtos = friendRequestService.getFriendRequest(loggedInUserId);
        List<UserDto> unreadMessages = userService.getUnreadMessages(loggedInUserId);
        friendDtos = userService.getFriendsList(loggedInUserId);
        UserDto currentUserDto =  userService.getUserName(loggedInUserId);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        modelAndView.addObject("unreadMessages",unreadMessages);
        modelAndView.addObject("userInfo",currentUserDto);

        return modelAndView;

    }

    @GetMapping(value = "{id}")
    public ModelAndView messageHistory(@PathVariable int id){

        getUserId();

        if(!(friendRequestService.checkFriendship(loggedInUserId,id))){
        return new ModelAndView("redirect:/");
        }

        UserDto currentUserDto =  userService.getUserName(loggedInUserId);
        messageDao.markUnreadMessagesAsReaded(loggedInUserId,id);
        List<UserDto> unreadMessages = userService.getUnreadMessages(loggedInUserId);
        List<RequestDto> requestDtos = friendRequestService.getFriendRequest(loggedInUserId);
        List<MessageDto> messageHistory = messageService.getMessageHistory(loggedInUserId,id);
        friendDtos = userService.getFriendsList(loggedInUserId);

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("blockStatus" , friendsDao.getBlockStatus(loggedInUserId,id));
        modelAndView.addObject("friendrequest",requestDtos);
        modelAndView.addObject("friendDtos", friendDtos);
        modelAndView.addObject("messagehistory",messageHistory);
        modelAndView.addObject("loggedInUserId", loggedInUserId);
        modelAndView.addObject("friend",userService.getUserName(id));
        modelAndView.addObject("unreadMessages",unreadMessages);
        modelAndView.addObject("userInfo",currentUserDto);

        return modelAndView;
    }

    @PostMapping(value = "friendrequest", consumes = "application/json")
    public ResponseEntity sendFriendRequest(@RequestBody FriendRequest friendRequest){
        getUserId();
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
        getUserId();
        messageDao.markUnreadMessagesAsReaded(loggedInUserId,Integer.parseInt(personId));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "blockMessages",consumes = "application/json")
    public  ResponseEntity blockUnblock(@RequestBody BlockFriend blockFriend){
        getUserId();
        friendsDao.blockUser(loggedInUserId,blockFriend.getUserId(),blockFriend.getBlock());
        return  ResponseEntity.ok().build();
    }
}
