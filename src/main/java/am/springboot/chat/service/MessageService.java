package am.springboot.chat.service;

import am.springboot.chat.dto.MessageDto;
import am.springboot.chat.entity.MessagesEntity;
import am.springboot.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getMessageHistory(int loggedInUserId, int friendId) {
        List<MessagesEntity> messagesEntities = messageRepository.findBy(loggedInUserId,friendId);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (MessagesEntity messagesEntity : messagesEntities) {
            messageDtos.add(new MessageDto(messagesEntity.getMessageFromId(),
                    messagesEntity.getMessageToId(),
                    messagesEntity.getMessage(),messagesEntity.getRegisterDate()));
        }
        return messageDtos;
    }
}
