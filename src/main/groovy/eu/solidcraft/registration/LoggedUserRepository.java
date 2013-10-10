package eu.solidcraft.registration;

public interface LoggedUserRepository {
    void login(User user);
    User getLoggedUser();
}
