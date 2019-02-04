package am.springboot.chat.Repository;

import am.springboot.chat.Entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends JpaRepository<FriendRequestEntity,Long> {



}
