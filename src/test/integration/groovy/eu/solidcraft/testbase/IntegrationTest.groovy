package eu.solidcraft.testbase

import eu.solidcraft.registration.User
import eu.solidcraft.registration.Workshop
import org.junit.After
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ["classpath:/spring/test.ioc.xml"])
abstract class IntegrationTest {
    @Autowired
    protected MongoTemplate mongoTemplate

    @After
    void cleanMongo() {
        mongoTemplate.dropCollection(Workshop.class)
        mongoTemplate.dropCollection(User.class)
    }
}
