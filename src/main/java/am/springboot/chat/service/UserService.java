package am.springboot.chat.service;

import am.springboot.chat.dto.FriendDto;
import am.springboot.chat.dto.UserDto;
import am.springboot.chat.entity.UserEntity;
import am.springboot.chat.repository.UserRepository;
import am.springboot.chat.domain.RegisterRequest;
import am.springboot.chat.domain.UserDomain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(RegisterRequest registerRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerRequest.getFirstName());
        userEntity.setLastName(registerRequest.getLastName());
        userEntity.setUserLogin(registerRequest.getLogin());
        userEntity.setUserPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserEntity> userEntitys = userRepository.findByUserLogin(login);

        return userEntitys
                .map(userEntity -> new UserDomain(
                        userEntity.getUserLogin(),
                        userEntity.getUserPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(userEntity.getUserRole())),
                        userEntity.getUserId()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
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
