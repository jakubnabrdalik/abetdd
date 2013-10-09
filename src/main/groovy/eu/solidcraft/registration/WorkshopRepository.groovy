package eu.solidcraft.registration
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkshopRepository extends MongoRepository<Workshop, String> {
}
