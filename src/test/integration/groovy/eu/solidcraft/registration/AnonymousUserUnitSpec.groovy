package eu.solidcraft.registration
import eu.solidcraft.generators.UserGenerator
import spock.lang.Specification

class AnonymousUserUnitSpec extends Specification {
    LoggedUserRepository loggedUserRepository = Mock(LoggedUserRepository)
    UserRepository userRepository = Stub(UserRepository)
    UserGenerator userGenerator = new UserGenerator(userRepository)
    WelcomeController controller = new WelcomeController(Stub(WorkshopRepository), loggedUserRepository, userRepository)

    def "should allow me to login with email"() {
        given:
            User user = userGenerator.saveNew("jnb@solidcraft.eu")
            userRepository.findOne(user.email) >> user

        when:
            controller.login(user.email)

        then:
            1 * loggedUserRepository.login(user)

    }

    def "login should throw exception if no user with given email found"() {
        given:
            userRepository.findOne(_) >> null

        when:
            controller.login("some@bad.email")

        then:
            thrown(NoSuchUserException)
    }
}
