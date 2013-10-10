package eu.solidcraft.registration

import static org.springframework.util.Assert.notNull

class BySessionSorter {
    List<Workshop> filterBySession(List<Workshop> workshops, Session session) {
        notNull(session); notNull(workshops)
        workshops.findAll {it.session == session}
    }
}
