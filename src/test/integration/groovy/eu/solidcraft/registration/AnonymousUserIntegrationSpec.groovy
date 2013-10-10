package eu.solidcraft.registration

import eu.solidcraft.generators.UserGenerator
import eu.solidcraft.generators.WorkshopGenerator
import eu.solidcraft.testbase.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class AnonymousUserIntegrationSpec extends IntegrationSpec {
    @Autowired
    WelcomeController controller

    @Autowired
    WorkshopRepository workshopRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    LoggedUserRepository loggedUserRepository

    WorkshopGenerator workshopGenerator
    UserGenerator userGenerator

    def setup() {
        workshopGenerator = new WorkshopGenerator(workshopRepository)
        userGenerator = new UserGenerator(userRepository)
    }


    def "should show lists of morning and evening workshops"() {
        given:
            Workshop eveningWorkshop = workshopGenerator.saveNew(Session.EVENING)
            Workshop morningWorkshop = workshopGenerator.saveNew(Session.MORNING)

        when:
            Map workshops = controller.list()

        then:
            workshops.morningWorkshops.find{it.name == morningWorkshop.name}
            workshops.eveningWorkshops.find{it.name == eveningWorkshop.name}
    }

    def "should allow me to login with email"() {
        given:
            User user = userGenerator.saveNew("jnb@solidcraft.eu")

        when:
            controller.login(user.email)

        then:
            loggedUserRepository.getLoggedUser() == user
    }

    def "login should throw exception if no user with given email found"() {
        when:
            controller.login("some@bad.email")

        then:
            thrown(NoSuchUserException)
    }

}
