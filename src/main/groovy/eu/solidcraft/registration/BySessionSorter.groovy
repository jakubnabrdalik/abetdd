package eu.solidcraft.registration

class BySessionSorter {
    List<Workshop> filterBySession(List<Workshop> workshops, Session session) {
        workshops.findAll {it.session == session}
    }
}
