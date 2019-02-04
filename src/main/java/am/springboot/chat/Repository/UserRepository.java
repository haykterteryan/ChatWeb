package am.springboot.chat.Repository;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.Entity.MessagesEntity;
import am.springboot.chat.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserLogin(String login);
    List<UserEntity> findByFirstName(String name);

    @Query("Select users from UserEntity users " +
            " inner JOIN users.friendsEntity friends " +
            " where friends.userId = :id")
    List<UserEntity> findByUserId(@Param("id") int id);
    
    @Query("Select message from MessagesEntity message" +
            " where (message_from_id = :fromId and message_to_id = :toId) " +
            " or (message_from_id = :toId and message_to_id= :fromId )")
    List<MessagesEntity> findBy(@Param("fromId") int from, @Param("toId") int to);
}
