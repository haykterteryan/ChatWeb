package am.springboot.chat.Controllers;

import am.springboot.chat.Dao.MessageDao;
import am.springboot.chat.domain.SocketMessage;
import am.springboot.chat.domain.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
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
        int loggedInUserId = loggedInUser.getUserId();

        messageDao.sendMessageToDb(loggedInUserId,socketMessage.getPersonId(),
                socketMessage.getMessage(),false);

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (Object principal :
                allPrincipals) {
            UserDomain userDomain = (UserDomain)principal;
            if(userDomain.getUserId() == socketMessage.getPersonId()){
                socketMessage.setPersonId(loggedInUserId);
                simpMessagingTemplate.convertAndSendToUser(userDomain.getUsername(),"/message",socketMessage);
                break;
            }
        }
        return ResponseEntity.ok().build();

    }

}
