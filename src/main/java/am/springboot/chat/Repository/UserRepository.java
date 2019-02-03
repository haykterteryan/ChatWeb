package am.springboot.chat.Repository;

import am.springboot.chat.DTO.FriendsList;
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
}
