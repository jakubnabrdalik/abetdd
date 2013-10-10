package eu.solidcraft.registration;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

@Controller
public class MyWorkshopsController {
    private LoggedUserRepository loggedUserRepository;
    private WorkshopRepository workshopRepository;
    private BySessionSorter bySessionSorter = new BySessionSorter();

    @Autowired
    public MyWorkshopsController(LoggedUserRepository loggedUserRepository, WorkshopRepository workshopRepository) {
        this.loggedUserRepository = loggedUserRepository;
        this.workshopRepository = workshopRepository;
    }

    @RequestMapping(value = "/myWorkshops", method = RequestMethod.GET)
    public Map myWorkshops() {
        User user = loggedUserRepository.getLoggedUser();
        List<Workshop> myWorkshops = workshopRepository.findByStudents (user);
        List<Workshop> availableWorkshops = newArrayList(filter(workshopRepository.findAll(), new SessionsNotInPredicate(myWorkshops)));
        List<Workshop> morningWorkshops = bySessionSorter.filterBySession(availableWorkshops, Session.MORNING);
        List<Workshop> eveningWorkshops = bySessionSorter.filterBySession(availableWorkshops, Session.EVENING);
        return ImmutableMap.of("myWorkshops", myWorkshops, "user", user,
                "availableMorningWorkshops", morningWorkshops, "availableEveningWorkshops", eveningWorkshops);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("workshopName") String workshopName) {
        User user = loggedUserRepository.getLoggedUser();
        Workshop workshop = workshopRepository.findOne(workshopName);
        verifyWorkshopExists(workshopName, workshop);
        verifyNotYetRegisteredForSameSession(user, workshop);
        workshop.register(user);
        workshopRepository.save(workshop);
        return "redirect:/myWorkshops";
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public String unregister(@RequestParam("workshopName") String workshopName) {
        User user = loggedUserRepository.getLoggedUser();
        Workshop workshop = workshopRepository.findOne(workshopName);
        verifyWorkshopExists(workshopName, workshop);
        workshop.unregister(user);
        workshopRepository.save(workshop);
        return "redirect:/myWorkshops";
    }

    private void verifyNotYetRegisteredForSameSession(User user, Workshop workshop) {
        Session sessionToBeRegisteredFor = workshop.getSession();
        for( Workshop alreadyRegisteredFor : workshopRepository.findByStudents(user) ) {
            if(alreadyRegisteredFor.getSession() == sessionToBeRegisteredFor) {
                throw new AlreadyRegisteredInThisSession();
            }
        }
    }

    private void verifyWorkshopExists(String workshopName, Workshop workshop) {
        if(workshop == null) {
            throw new RuntimeException("No workshop with name " + workshopName);
        }
    }

    public class AlreadyRegisteredInThisSession extends RuntimeException {
    }
}
