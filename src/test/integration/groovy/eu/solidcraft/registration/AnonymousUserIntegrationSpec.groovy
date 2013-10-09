package eu.solidcraft.registration

import eu.solidcraft.generators.WorkshopGenerator
import eu.solidcraft.testbase.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class AnonymousUserIntegrationSpec extends IntegrationSpec {
    @Autowired
    WelcomeController controller

    @Autowired
    WorkshopRepository workshopRepository

    WorkshopGenerator generator

    def setup() {
        generator = new WorkshopGenerator(workshopRepository)
    }


    def "should show lists of morning and evening workshops"() {
        given:
            Workshop eveningWorkshop = generator.saveNew(Session.EVENING)
            Workshop morningWorkshop = generator.saveNew(Session.MORNING)

        when:
            Map workshops = controller.list()

        then:
            workshops.morningWorkshops.find{it.name == morningWorkshop.name}
            workshops.eveningWorkshops.find{it.name == eveningWorkshop.name}
    }

    def "should allow me to login with email"() {

    }

    def "login should throw exception if no user with given email found"() {

    }
}
