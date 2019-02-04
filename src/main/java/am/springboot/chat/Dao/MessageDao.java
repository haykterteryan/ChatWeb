package am.springboot.chat.Dao;

import am.springboot.chat.DTO.MessageDto;
import am.springboot.chat.Entity.MessagesEntity;
import am.springboot.chat.Repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao {

    UserRepository userRepository;

    public MessageDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<MessageDto> getMessageHistory(int loggedInUserId, int friendid) {
        List<MessagesEntity> messagesEntities = userRepository.findBy(loggedInUserId,friendid);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (MessagesEntity messagesEntity : messagesEntities) {
            messageDtos.add(new MessageDto(messagesEntity.getMessageFromId(),
                    messagesEntity.getMessageToId(),
                    messagesEntity.getMessage(),messagesEntity.getRegisterDate()));
        }
        return messageDtos;
    }
}
