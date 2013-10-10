package eu.solidcraft.registration

class StatisticsGenerator {
    Map getStudentsByWorkshopAndRoles(List<Workshop> workshops) {
        workshops.collectEntries {
            [(it.name) :it.students.groupBy {it.role}]
        }
    }
}
