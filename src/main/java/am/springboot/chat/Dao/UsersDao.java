package am.springboot.chat.Dao;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.SearchUserDto;
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

    public List<SearchUserDto> loadUserByname(String name) {
        List<SearchUserDto> searchUserDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByFirstName(name);

        for (UserEntity userEntity : userEntities
             ) {
            searchUserDtos.add(new SearchUserDto(userEntity.getFirstName(),userEntity.getLastName()));
        }

        return searchUserDtos;
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


}
