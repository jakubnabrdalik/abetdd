package eu.solidcraft.registration;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Controller
public class WelcomeController {
    private WorkshopRepository workshopRepository;
    private BySessionSorter bySessionSorter = new BySessionSorter();
    private LoggedUserRepository loggedUserRepository;
    private UserRepository userRepository;

    @Autowired
    public WelcomeController(WorkshopRepository workshopRepository, LoggedUserRepository loggedUserRepository, UserRepository userRepository) {
        notNull(workshopRepository);
        this.workshopRepository = workshopRepository;
        this.loggedUserRepository = loggedUserRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String poorManIndex() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map list() {
        List<Workshop> workshops = workshopRepository.findAll();
        List<Workshop> morningWorkshops = bySessionSorter.filterBySession(workshops, Session.MORNING);
        List<Workshop> eveningWorkshops = bySessionSorter.filterBySession(workshops, Session.EVENING);
        return ImmutableMap.of("morningWorkshops", morningWorkshops, "eveningWorkshops", eveningWorkshops);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email) {
        User user = userRepository.findOne(email);
        verifyUserExists(user);
        loggedUserRepository.login(user);
        return "redirect:/myWorkshops";
    }

    private void verifyUserExists(User user) {
        if(user == null) {
            throw new NoSuchUserException();
        }
    }
}
