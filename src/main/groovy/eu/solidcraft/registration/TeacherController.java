package eu.solidcraft.registration;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {
    private WorkshopRepository workshopRepository;
    private StatisticsGenerator statisticsGenerator = new StatisticsGenerator();

    @Autowired
    public TeacherController(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @RequestMapping(value = "/teacherLogin", method = RequestMethod.GET)
    public void teacherLogin() {

    }

    @RequestMapping(value = "/teachersWorkshops", method = RequestMethod.POST)
    public Map statistics(@RequestParam("teachersEmail") String teachersEmail) {
        List<Workshop> workshops = workshopRepository.findByTeachersEmail (teachersEmail);
        verifyWorkshopsExists(teachersEmail, workshops);
        return ImmutableMap.of("workshops", statisticsGenerator.getStudentsByWorkshopAndRoles(workshops));
    }

    private void verifyWorkshopsExists(String teachersEmail, List<Workshop> workshops) {
        if(workshops.isEmpty()) {
            throw new RuntimeException("No workshops for teachersEmail: " + teachersEmail);
        }
    }
}