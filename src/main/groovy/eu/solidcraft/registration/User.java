package eu.solidcraft.registration;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class User {
    @Id
    private String email;
    private String role;
    private String name;

    @Version
    private Long version;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String role, String name) {
        this.email = email;
        this.role = role;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (version != null ? !version.equals(user.version) : user.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
