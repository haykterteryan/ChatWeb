package am.springboot.chat.Controllers;

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

    @GetMapping(value="/{username}", params = "search")
    public ResponseEntity<List<SearchUserDto>> search(@RequestParam(name = "search") String name){
        List<SearchUserDto> searchUserDtos = usersDao.loadUserByname(name);
        return  searchUserDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(searchUserDtos);

    }

    @GetMapping(path = "{username}")
    public ModelAndView homepage(@PathVariable("username") String username){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject();
        return modelAndView;


    }

}
