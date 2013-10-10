package eu.solidcraft.registration;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Controller
public class WelcomeController {
    private WorkshopRepository workshopRepository;
    private BySessionSorter bySessionSorter = new BySessionSorter();

    @Autowired
    public WelcomeController(WorkshopRepository workshopRepository) {
        notNull(workshopRepository);
        this.workshopRepository = workshopRepository;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map list() {
        List<Workshop> workshops = workshopRepository.findAll();
        List<Workshop> morningWorkshops = bySessionSorter.filterBySession(workshops, Session.MORNING);
        List<Workshop> eveningWorkshops = bySessionSorter.filterBySession(workshops, Session.EVENING);
        return ImmutableMap.of("morningWorkshops", morningWorkshops, "eveningWorkshops", eveningWorkshops);
    }
}
