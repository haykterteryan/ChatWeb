package am.springboot.chat.Controllers;

import am.springboot.chat.DTO.FriendsList;
import am.springboot.chat.DTO.SearchUserDto;
import am.springboot.chat.Dao.UsersDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {

    UsersDao usersDao;

    public HomeController(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @GetMapping(value="/{id}", params = "search")
    public ModelAndView search(@RequestParam(name = "search") String name){
        ModelAndView modelAndView = new ModelAndView("index");
        List<SearchUserDto> searchUserDtos = usersDao.loadUserByname(name);
        modelAndView.addObject("searchresult",searchUserDtos);
        return  modelAndView;

    }

    @GetMapping(path = "{id}")
    public ModelAndView homepage(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView("index");
        List<FriendsList> friendsLists = usersDao.getFriendsList(id);
        modelAndView.addObject("friendsLists", friendsLists);
        return modelAndView;
    }

}
