package eu.solidcraft.testbase

import eu.solidcraft.registration.User
import eu.solidcraft.registration.Workshop
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = ["classpath:/spring/test.ioc.xml", "classpath:/spring/webmvc.ioc.xml"])
abstract class IntegrationSpec extends Specification {
    @Autowired protected MongoTemplate mongoTemplate

    def cleanup() {
        mongoTemplate.dropCollection(Workshop.class)
        mongoTemplate.dropCollection(User.class)
    }
}
