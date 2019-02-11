package am.springboot.chat.repository;


import am.springboot.chat.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserLogin(String login);

    @Query("Select users from UserEntity users where users.userId Not In (" +
            " select u.userId from  UserEntity u " +
            "inner join u.friendsEntity friends" +
            " where friends.friendsId = :id ) and users.userId <> :id and " +
            "( users.firstName = :name or users.lastName = :name )")
    List<UserEntity> searchById( @Param("id") int id, @Param("name") String name);

    @Query("Select users from UserEntity users " +
            " inner JOIN users.friendsEntity friends " +
            " where friends.userId = :id")
    List<UserEntity> findByUserId(@Param("id") int id);
    


    @Query("Select users from UserEntity users " +
            "inner Join users.friendRequestEntities request" +
            " where request_to_id = :id and readed = false ")
    List<UserEntity> findByRequestToId(@Param("id") int id);

    @Query("Select DISTINCT users from UserEntity users " +
            "inner join users.usersMessageToEntity " +
            " where message_to_id = :toId and readed=false")
    List<UserEntity> findByUserIdDistinct(@Param("toId") int loggedInUserId);

    UserEntity getByUserId(int id);


}
