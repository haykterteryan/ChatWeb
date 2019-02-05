package am.springboot.chat.Dao;

import am.springboot.chat.DTO.MessageDto;
import am.springboot.chat.Entity.MessagesEntity;
import am.springboot.chat.Repository.MessageRepository;
import am.springboot.chat.Repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao {

    MessageRepository messageRepository;
    private final JdbcTemplate jdbcTemplate;

    public MessageDao( JdbcTemplate jdbcTemplate, MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<MessageDto> getMessageHistory(int loggedInUserId, int friendid) {
        List<MessagesEntity> messagesEntities = messageRepository.findBy(loggedInUserId,friendid);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (MessagesEntity messagesEntity : messagesEntities) {
            messageDtos.add(new MessageDto(messagesEntity.getMessageFromId(),
                    messagesEntity.getMessageToId(),
                    messagesEntity.getMessage(),messagesEntity.getRegisterDate()));
        }
        return messageDtos;
    }

    public void sendMessageToDb(int loggedInUserId, int toId, String message,boolean readed) {
        String query ="insert into messages(message_from_id, message_to_id, message, readed) values (?,?,?,?) ";
        jdbcTemplate.update(query,loggedInUserId,toId,message,readed);
    }
}
