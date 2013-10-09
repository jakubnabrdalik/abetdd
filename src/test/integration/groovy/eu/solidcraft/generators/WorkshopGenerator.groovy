package eu.solidcraft.generators
import eu.solidcraft.registration.Session
import eu.solidcraft.registration.Workshop
import eu.solidcraft.registration.WorkshopRepository

class WorkshopGenerator {
    private WorkshopRepository workshopRepository
    private static int nameNumber = 0

    WorkshopGenerator(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository
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
