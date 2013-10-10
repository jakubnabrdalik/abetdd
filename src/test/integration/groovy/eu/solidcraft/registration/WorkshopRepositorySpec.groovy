package eu.solidcraft.registration

import eu.solidcraft.generators.UserGenerator
import eu.solidcraft.generators.WorkshopGenerator
import eu.solidcraft.testbase.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class WorkshopRepositorySpec extends IntegrationSpec {
    @Autowired WorkshopRepository workshopRepository
    @Autowired UserRepository userRepository
    WorkshopGenerator workshopGenerator
    UserGenerator userGenerator
    User user

    def setup() {
        workshopGenerator = new WorkshopGenerator(workshopRepository)
        userGenerator = new UserGenerator(userRepository)
        user = userGenerator.saveNew()
    }

    void "should find workshops by student"() {
        given:
            workshopGenerator.saveNewAndRegister(Session.MORNING, user)
            workshopGenerator.saveNewAndRegister(Session.EVENING, user)
            workshopGenerator.saveNew()

        when:
            List<Workshop> workshops = workshopRepository.findByStudents(user)

        then:
            workshops.size() == 2
    }
}
