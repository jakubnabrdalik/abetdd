package eu.solidcraft.generators
import eu.solidcraft.registration.Session
import eu.solidcraft.registration.User
import eu.solidcraft.registration.Workshop
import eu.solidcraft.registration.WorkshopRepository

class WorkshopGenerator {
    private WorkshopRepository workshopRepository
    private static int nameNumber = 0

    WorkshopGenerator(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository
    }

    Workshop saveNewAndRegister(User user) {
        return saveNewAndRegister(Session.MORNING, user)
    }

    Workshop saveNewAndRegister(Session session, User user) {
        Workshop workshop = saveNew(session)
        workshop.register(user)
        workshopRepository.save(workshop)
        return workshop
    }

    Workshop saveNew() {
        return saveNew([:])
    }

    Workshop saveNew(Map properties) {
        Workshop workshop = create()
        properties.each { String key, Object value ->
            workshop.("$key") = value
        }
        workshopRepository.save(workshop)
        return workshop
    }

    Workshop saveNew(Session session) {
        Workshop workshop = create()
        workshop.session = session
        workshopRepository.save(workshop)
        return workshop
    }

    List<Workshop> saveNew(Session session, int howMany) {
        return (1..howMany).collect{saveNew(session)}
    }

    private Workshop create() {
        Workshop workshop = new Workshop()
        workshop.limit = 100
        workshop.name = "workshop" + nameNumber++
        workshop.room = "5"
        workshop.teacher = "John"
        workshop.teachersEmail = "john@gov.pl"
        return workshop
    }
}