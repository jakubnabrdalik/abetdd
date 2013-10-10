package eu.solidcraft.registration

import eu.solidcraft.generators.UserGenerator
import eu.solidcraft.generators.WorkshopGenerator
import eu.solidcraft.testbase.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class LoggedStudentIntegrationSpec extends IntegrationSpec {
    @Autowired WorkshopRepository workshopRepository
    @Autowired UserRepository userRepository
    @Autowired LoggedUserRepository loggedUserRepository
    @Autowired MyWorkshopsController controller
    WorkshopGenerator workshopGenerator
    UserGenerator userGenerator
    User user

    def setup() {
        workshopGenerator = new WorkshopGenerator(workshopRepository)
        userGenerator = new UserGenerator(userRepository)
        user = userGenerator.saveNew()
        userRepository.save(user)
        loggedUserRepository.login(user)
    }

    def "should see what I've registered for"() {
        given:
            Workshop morningWorkshop = workshopGenerator.saveNewAndRegister(Session.MORNING, user)
            Workshop eveningWorkshop = workshopGenerator.saveNewAndRegister(Session.EVENING, user)

        when:
            Map map = controller.myWorkshops()

        then:
            List<Workshop> workshops = map.myWorkshops
            workshops.size() == 2
            containsWorkshop(workshops, morningWorkshop)
            containsWorkshop(workshops, eveningWorkshop)
    }

    private void containsWorkshop(List<Workshop> workshops, eveningWorkshop) {
        workshops.find { it.name == eveningWorkshop.name } != null
    }

    def "should see workshops still available to me in the morning and in the evening"() {
        given:
            workshopGenerator.saveNewAndRegister(Session.MORNING, user)
            Workshop eveningWorkshop = workshopGenerator.saveNew(Session.EVENING)

        when:
            Map map = controller.myWorkshops()

        then:
            List<Workshop> workshops = map.availableEveningWorkshops
            workshops.size() == 1
            containsWorkshop(workshops, eveningWorkshop)
    }

    def "should be able to register for a workshop"() {
        given:
            Workshop workshop = workshopGenerator.saveNew()

        when:
            controller.register(workshop.name)

        then:
            workshopRepository.findOne(workshop.name).students.contains(user)
    }

    def "should not be able to register for a workshop if I'm already registered for a workshop in that session"() {
        given:
            workshopGenerator.saveNewAndRegister(Session.MORNING, user)
            Workshop secondMorningWorkshop = workshopGenerator.saveNew(Session.MORNING)
        when:
            controller.register(secondMorningWorkshop.name)
        then:
            thrown(MyWorkshopsController.AlreadyRegisteredInThisSession)
    }

    def "should not be able to register for a workshop I'm already registerd to it"() {
        given:
            Workshop workshop = workshopGenerator.saveNewAndRegister(Session.MORNING, user)

        when:
            controller.register(workshop.name)

        then:
            thrown(MyWorkshopsController.AlreadyRegisteredInThisSession)
    }

    def "should not be able to register for a workshop if it's full"() {
        given:
            Workshop workshop = workshopGenerator.saveNew(limit: 1)
            workshop.register(userGenerator.saveNew())
            workshopRepository.save(workshop)

        when:
            controller.register(workshop.name)

        then:
            thrown(Workshop.WorkshopIsFull)
    }

    def "should be able to unregister from a workshop"() {
        given:
            Workshop workshop = workshopGenerator.saveNewAndRegister(Session.MORNING, user)

        when:
            controller.unregister(workshop.name)

        then:
            !workshopRepository.findOne(workshop.name).students.contains(user)
    }

    def "should not be able to unregister if I'm not registered for that workshop"() {
        given:
            Workshop workshop = workshopGenerator.saveNew()

        when:
            controller.unregister(workshop.name)

        then:
            thrown(RuntimeException)
    }


}
