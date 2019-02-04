package am.springboot.chat.Dao;

import am.springboot.chat.Repository.RequestRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FriendRequestDao {

    RequestRepository requestRepository;
    private final JdbcTemplate jdbcTemplate;

    public FriendRequestDao(RequestRepository requestRepository, JdbcTemplate jdbcTemplate) {
        this.requestRepository = requestRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void sendFriendRequest(int loggedInUserId, int toId) {
        String query = "Insert into friend_request (request_from_id, request_to_id,readed)" +
                " values (?,?,false)";
        jdbcTemplate.update(query,loggedInUserId,toId);
    }
}
