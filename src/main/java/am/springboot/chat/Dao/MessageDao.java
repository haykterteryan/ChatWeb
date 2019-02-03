package am.springboot.chat.Dao;

import am.springboot.chat.Repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao {

    UserRepository userRepository;

    public MessageDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



}
