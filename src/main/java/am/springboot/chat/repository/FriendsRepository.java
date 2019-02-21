package am.springboot.chat.repository;

import am.springboot.chat.entity.FriendsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendsRepository extends CrudRepository<FriendsEntity, Long> {


    @Query("SELECT friend from FriendsEntity friend " +
            " where (friend.userId = :senderId and friend.friendId = :receiverId and friend.blockStatus = true )" +
            "or (friend.userId = :receiverId and friend.friendId = :senderId and friend.blockStatus = true ) ")
    List<FriendsEntity> findFriendship(@Param("senderId") int senderId, @Param("receiverId") int receiverId);

    @Query("SELECT friend from FriendsEntity friend " +
            " where friend.userId = :senderId and friend.friendId = :receiverId ")
    List<FriendsEntity> findFriend(@Param("senderId")int senderId,@Param("receiverId") int receiverId);


    @Query("SELECT friend.blockStatus from FriendsEntity friend " +
            " where friend.userId = :senderId and friend.friendId = :receiverId ")
    boolean findByUserIdAndFriendId(@Param("senderId") int loggedInUserId, @Param("receiverId") int id);
}
