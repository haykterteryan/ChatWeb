package am.springboot.chat.Dao;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.UserDto;
import am.springboot.chat.Entity.UserEntity;
import am.springboot.chat.Repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersDao {

    UserRepository userRepository;


    public UsersDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> loadUserByname(String name) {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByFirstName(name);

        for (UserEntity userEntity : userEntities
             ) {
            userDtos.add(new UserDto(userEntity.getFirstName(),userEntity.getLastName()));
        }

        return userDtos;
    }

    public List<FriendsList> getFriendsList(int userId){
        List<FriendsList> friendsLists = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByUserId(userId);

        for (UserEntity userEntity :
                userEntities) {
            friendsLists.add(new FriendsList(userEntity.getUserId(), userEntity.getFirstName(), userEntity.getLastName()));
        }

        return friendsLists;
    }

    public UserDto getUserName(int userId) {
        UserEntity userEntity = userRepository.getByUserId(userId);
        return new UserDto(userEntity.getFirstName(),userEntity.getLastName());
    }

    public List<UserDto> getUnreadMessages(int loggedInUserId) {

        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByUserIdDistinct(loggedInUserId);

        for (UserEntity userEntity : userEntities
             ) {
            userDtos.add(new UserDto(userEntity.getFirstName(),userEntity.getLastName()));
        }
//  userRepository.findByUserIdDistinct(loggedInUserId).stream().
//                map(userEntity -> (userDtos.add(new UserDto(userEntity.getFirstName(),
//                        userEntity.getLastName()))));

        return userDtos;
    }


}
