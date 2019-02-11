package am.springboot.chat.dao;

import am.springboot.chat.dto.FriendDto;
import am.springboot.chat.dto.UserDto;
import am.springboot.chat.entity.UserEntity;
import am.springboot.chat.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class UsersDao {

    UserRepository userRepository;


    public UsersDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> loadUserByname(String name, int loggedInUserId) {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.searchById(loggedInUserId,name);

        for (UserEntity userEntity : userEntities) {
            userDtos.add(new UserDto(userEntity.getUserId(),userEntity.getFirstName(),userEntity.getLastName()));
        }

        return userDtos;
    }

    public List<FriendDto> getFriendsList(int userId){
        List<FriendDto> friendDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByUserId(userId);

        for (UserEntity userEntity :
                userEntities) {
            friendDtos.add(new FriendDto(userEntity.getUserId(), userEntity.getFirstName(), userEntity.getLastName()));
        }

        return friendDtos;
    }

    public UserDto getUserName(int userId) {
        UserEntity userEntity = userRepository.getByUserId(userId);
        return new UserDto(userEntity.getUserId(),userEntity.getFirstName(),userEntity.getLastName());
    }

    public List<UserDto> getUnreadMessages(int loggedInUserId) {

        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByUserIdDistinct(loggedInUserId);

        for (UserEntity userEntity : userEntities
             ) {
            userDtos.add(new UserDto(userEntity.getFirstName(),userEntity.getLastName()));
        }

        return userDtos;
    }




}
