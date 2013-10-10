package eu.solidcraft.registration;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Document
public class Workshop {

    @Id
    String name;
    String teacher;
    String teachersEmail;
    String room;
    int limit;
    Session session;

    Set<User> students = newHashSet();

    @Version
    private Long version;

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTeachersEmail() {
        return teachersEmail;
    }

    public String getRoom() {
        return room;
    }

    public Set<User> getStudents() {
        return students;
    }

    public Session getSession() {
        return session;
    }

    public int getLimit() {
        return limit;
    }
}
