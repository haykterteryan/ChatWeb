package am.springboot.chat.Repository;

import am.springboot.chat.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserLogin(String login);
    List<UserEntity> findByFirstName(String name);
}
