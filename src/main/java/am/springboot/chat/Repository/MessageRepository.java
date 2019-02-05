package am.springboot.chat.Repository;

import am.springboot.chat.Entity.MessagesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessagesEntity,Long> {


    @Query("Select message from MessagesEntity message" +
            " where (message_from_id = :fromId and message_to_id = :toId) " +
            " or (message_from_id = :toId and message_to_id= :fromId )")
    List<MessagesEntity> findBy(@Param("fromId") int from, @Param("toId") int to);
}
