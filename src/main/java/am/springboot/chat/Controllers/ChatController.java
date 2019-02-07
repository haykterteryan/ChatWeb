package am.springboot.chat.Controllers;

import am.springboot.chat.domain.SocketMessage;
import am.springboot.chat.domain.UserDomain;
import javafx.application.Application;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final SessionRegistry sessionRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SessionRegistry sessionRegistry, SimpMessagingTemplate simpMessagingTemplate) {
        this.sessionRegistry = sessionRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping(value = "/send", consumes = "application/json")
    public ResponseEntity<SocketMessage> send(@RequestBody SocketMessage socketMessage ) {

        UserDomain loggedInUser = (UserDomain) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        int loggedInId = loggedInUser.getUserId();


        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (Object principal : allPrincipals
             ) {
            UserDomain userDomain = (UserDomain)principal;
            if(userDomain.getUserId() == socketMessage.getPersonId()){
                simpMessagingTemplate.convertAndSendToUser(userDomain.getUsername(),"/message",
                        MessageFormat.format("{0} : {1} : {2}",loggedInUser.getUsername(),
                                socketMessage.getMessage(),loggedInId));
            }
        }

        return ResponseEntity.ok().build();
    }

}
