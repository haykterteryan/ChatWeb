package am.springboot.chat.repository;

import am.springboot.chat.entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends CrudRepository<FriendRequestEntity,Long> {


    @Query("select request from FriendRequestEntity request" +
            " where (request.requestFromId =:userId and request.requestToId =:id and request.aprooved = true) or " +
            "(request.requestFromId =:id and request.requestToId =:userId and request.aprooved = true)")
    FriendRequestEntity findBy(@Param("userId") int userId,@Param("id") int id);
}
