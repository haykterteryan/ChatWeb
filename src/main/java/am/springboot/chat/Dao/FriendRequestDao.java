package am.springboot.chat.Dao;

import am.springboot.chat.DTO.RequestDto;
import am.springboot.chat.Entity.UserEntity;
import am.springboot.chat.Repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendRequestDao {


    UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    public FriendRequestDao(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void sendFriendRequest(int loggedInUserId, int toId) {
        String query = "Insert into friend_request (request_from_id, request_to_id,readed)" +
                " values (?,?,false)";
        jdbcTemplate.update(query,loggedInUserId,toId);
    }

    public List<RequestDto> getFriendRequest(int loggedInUserId) {
        List<UserEntity> friendRequestEntities = userRepository.findByRequestToId(loggedInUserId);
        List<RequestDto> requestDtos = new ArrayList<>();
        for (UserEntity userEntity:
             friendRequestEntities) {
            requestDtos.add(new RequestDto(userEntity.getUserId(),userEntity.getFirstName(),userEntity.getLastName()));
        }

        return requestDtos;
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
