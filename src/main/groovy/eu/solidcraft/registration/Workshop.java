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

    public void register(User user) {
        verifyLimit();
        verifyNotYetRegisteredHere(user);
        students.add(user);
    }

    private void verifyNotYetRegisteredHere(User user) {
        if(isRegisteredStudent(user)) {
            throw new AlreadyRegistered();
        }
    }

    private void verifyLimit() {
        if(limit != 0 && students.size() >= limit) {
            throw new WorkshopIsFull();
        }
    }

    public void unregister(User user) {
        verifyIsRegisteredHere(user);
        students.remove(user);
    }

    private void verifyIsRegisteredHere(User user) {
        if(!isRegisteredStudent(user)) {
            throw new NotYetRegistered();
        }
    }

    private boolean isRegisteredStudent(User user) {
        return students.contains(user) ? true : false;
    }

    public Integer howManySlotsAreFree() {
        return limit == 0 ? 100 : limit - students.size();
    }

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

    public class WorkshopIsFull extends RuntimeException {
    }

    public class AlreadyRegistered extends RuntimeException {
    }

    public class NotYetRegistered extends RuntimeException {
    }
}
