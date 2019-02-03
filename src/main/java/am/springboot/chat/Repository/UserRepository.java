package am.springboot.chat.Repository;

import am.springboot.chat.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserLogin(String login);
    List<UserEntity> findByFirstName(String name);

    @Query(" Select friends.user_id, friends.friend_id, u.first_name, u.last_name, u.user_id from friends " +
            " inner JOIN users u on friends.friend_id = u.user_id" +
            " where friends.user_id = :id")
    public List<UserEntity> findByUserId(@Param("id") int id);
}
