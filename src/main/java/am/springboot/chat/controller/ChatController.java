package am.springboot.chat.controller;

import am.springboot.chat.dao.MessageDao;
import am.springboot.chat.domain.SocketMessage;
import am.springboot.chat.domain.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final SessionRegistry sessionRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageDao messageDao;

    public ChatController(SessionRegistry sessionRegistry, SimpMessagingTemplate simpMessagingTemplate, MessageDao messageDao) {
        this.sessionRegistry = sessionRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageDao = messageDao;
    }

    @PostMapping(value = "/send", consumes = "application/json")
    public ResponseEntity<?> send(@RequestBody SocketMessage socketMessage ) {

        UserDomain loggedInUser = (UserDomain) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        socketMessage.setSenderId(loggedInUser.getUserId());
        socketMessage.setFirstName(loggedInUser.getFirstName());
        socketMessage.setLastName(loggedInUser.getLastName());

        messageDao.sendMessageToDb(loggedInUser.getUserId(),socketMessage.getReceiverId(),
                socketMessage.getMessage(),false);

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (Object principal :
                allPrincipals) {
            UserDomain userDomain = (UserDomain)principal;
            if(userDomain.getUserId() == socketMessage.getReceiverId()){
                simpMessagingTemplate.convertAndSendToUser(userDomain.getUsername(),"/message",socketMessage);
            }
        }
        return ResponseEntity.ok().build();

    }

}
