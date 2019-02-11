package am.springboot.chat.dao;

import am.springboot.chat.dto.RequestDto;
import am.springboot.chat.entity.FriendRequestEntity;
import am.springboot.chat.entity.UserEntity;
import am.springboot.chat.repository.FriendRequestRepository;
import am.springboot.chat.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendRequestDao {


    UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    FriendRequestRepository friendRequestRepository;

    public FriendRequestDao(UserRepository userRepository, JdbcTemplate jdbcTemplate,FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.friendRequestRepository = friendRequestRepository;
    }


    public void sendFriendRequest(int loggedInUserId, int toId) {
        String query = "Insert into friend_request (request_from_id, request_to_id,readed)" +
                " values (?,?,false)";
        jdbcTemplate.update(query,loggedInUserId,toId);
    }


    public void acceptOrDeniedFriendRequest(int accept,int loggedInUserId, int id ) {
        String query = "UPDATE friend_request SET readed=1, aproved=? " +
                "where request_to_id = ? and request_from_id = ?";

        jdbcTemplate.update(query,accept,loggedInUserId,id);

        if(accept == 1){
            addToFriendsList(loggedInUserId,id);
            addToFriendsList(id, loggedInUserId);
        }

    }

    private void addToFriendsList(int loggedInUserId, int id) {
        String query = "Insert into friends(user_id,friend_id) values (?,?)" ;

        jdbcTemplate.update(query,loggedInUserId,id);
    }



}
