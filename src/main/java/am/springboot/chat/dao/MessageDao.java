package am.springboot.chat.dao;

import am.springboot.chat.dto.MessageDto;
import am.springboot.chat.entity.MessagesEntity;
import am.springboot.chat.repository.MessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao {

    private final JdbcTemplate jdbcTemplate;

    public MessageDao( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean sendMessageToDb(int loggedInUserId, int toId, String message,boolean readed) {
        String query ="insert into messages(message_from_id, message_to_id, message, readed) values (?,?,?,?) ";
        return 1 == jdbcTemplate.update(query,loggedInUserId,toId,message,readed);

    }

    public void markUnreadMessagesAsReaded(int loggedInUserId,int friendId) {
        String query ="UPDATE messages SET readed = true where message_to_id = ? and message_from_id = ?";
        jdbcTemplate.update(query,loggedInUserId,friendId);
    }
}
