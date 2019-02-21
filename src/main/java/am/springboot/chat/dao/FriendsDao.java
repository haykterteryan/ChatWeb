package am.springboot.chat.dao;

import am.springboot.chat.entity.FriendsEntity;
import am.springboot.chat.repository.FriendsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendsDao {

    JdbcTemplate jdbcTemplate;
    FriendsRepository friendsRepository;

    public FriendsDao(JdbcTemplate jdbcTemplate, FriendsRepository friendsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendsRepository = friendsRepository;
    }

    public void blockUser(int loggedInUserId, int personId, int block) {
        String query = "update friends set block_status = ? where user_id=? and friend_id =?";
        jdbcTemplate.update(query, block, loggedInUserId, personId);
    }

    public boolean checkBlockStatus(int senderId, int receiverId) {
        return( !(friendsRepository.findFriendship(senderId,receiverId).isEmpty()));

    }

    public boolean checkFriendShip(int senderId, int receiverId) {
        List<FriendsEntity>  friendsEntities = friendsRepository.findFriend(senderId,receiverId);
        return !friendsEntities.isEmpty();

    }

    public boolean getBlockStatus(int loggedInUserId, int id) {
        return friendsRepository.findByUserIdAndFriendId(loggedInUserId,id);
    }
}
