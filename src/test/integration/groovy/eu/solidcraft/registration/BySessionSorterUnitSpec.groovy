
package eu.solidcraft.registration

import eu.solidcraft.generators.WorkshopGenerator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class BySessionSorterUnitSpec extends Specification {
    BySessionSorter sorter = new BySessionSorter()
    @Shared WorkshopRepository workshopRepository = Stub(WorkshopRepository)
    @Shared WorkshopGenerator workshopGenerator = new WorkshopGenerator(workshopRepository)

    @Unroll
    def "should filter by session"() {
        given:
            List workshops = (morningWorkshops + eveningWorkshops).flatten()

        when:
            List<Workshop> foundWorkshops = sorter.filterBySession(workshops, session)

        then:
            foundWorkshops.findAll{it.session == session}.size() == expectedNumber

        where:
            morningWorkshops | eveningWorkshops | session | expectedNumber
            workshopGenerator.saveNew(Session.EVENING, 1) | workshopGenerator.saveNew(Session.MORNING, 1) | Session.EVENING | 1
            workshopGenerator.saveNew(Session.EVENING, 1) | workshopGenerator.saveNew(Session.MORNING, 2) | Session.EVENING | 1
            workshopGenerator.saveNew(Session.EVENING, 2) | workshopGenerator.saveNew(Session.MORNING, 1) | Session.EVENING | 2
            workshopGenerator.saveNew(Session.MORNING, 1) | [] | Session.EVENING | 0
            workshopGenerator.saveNew(Session.EVENING, 1) | [] | Session.EVENING | 1
    }
}
