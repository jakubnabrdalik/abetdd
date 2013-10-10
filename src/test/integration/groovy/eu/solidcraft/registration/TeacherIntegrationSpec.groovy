package eu.solidcraft.registration

import eu.solidcraft.generators.UserGenerator
import eu.solidcraft.generators.WorkshopGenerator
import eu.solidcraft.testbase.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class TeacherIntegrationSpec extends IntegrationSpec {
    @Autowired TeacherController controller
    @Autowired WorkshopRepository workshopRepository
    @Autowired UserRepository userRepository
    WorkshopGenerator workshopGenerator
    UserGenerator userGenerator
    User user

    def setup() {
        workshopGenerator = new WorkshopGenerator(workshopRepository)
        userGenerator = new UserGenerator(userRepository)
        user = userGenerator.saveNew()
        userRepository.save(user)
    }

    def "should see who has registered for my workshops, grouped by student's role"() {
        given:
            Workshop workshop = workshopGenerator.saveNewAndRegister(Session.EVENING, user)

        when:
            Map model = controller.statistics(workshop.teachersEmail)

        then:
            model.workshops."${workshop.name}"."${user.role}".size() == 1
    }
}
