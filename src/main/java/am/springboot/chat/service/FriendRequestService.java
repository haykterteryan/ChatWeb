package am.springboot.chat.service;

import am.springboot.chat.dto.RequestDto;
import am.springboot.chat.entity.UserEntity;
import am.springboot.chat.repository.FriendRequestRepository;
import am.springboot.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestService {

    FriendRequestRepository friendRequestRepository;
    UserRepository userRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    public List<RequestDto> getFriendRequest(int loggedInUserId) {
        List<UserEntity> friendRequestEntities = userRepository.findByRequestToId(loggedInUserId);
        List<RequestDto> requestDtos = new ArrayList<>();
        for (UserEntity userEntity:
                friendRequestEntities) {
            requestDtos.add(new RequestDto(userEntity.getUserId(),userEntity.getFirstName(),userEntity.getLastName()));
        }

        return requestDtos;
    }

    public boolean checkFriendship(int loggedInUserId, int id) {
        return friendRequestRepository.findBy(loggedInUserId,id) !=null;

    }
}
