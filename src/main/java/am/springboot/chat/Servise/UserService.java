package am.springboot.chat.Servise;

import am.springboot.chat.Entity.UserEntity;
import am.springboot.chat.Repository.UserRepository;
import am.springboot.chat.domain.RegisterRequest;
import am.springboot.chat.domain.UserDomain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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


}
