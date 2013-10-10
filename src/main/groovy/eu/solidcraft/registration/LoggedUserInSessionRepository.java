package eu.solidcraft.registration;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class LoggedUserInSessionRepository implements LoggedUserRepository {
    private User loggedInUser;

    public void login(User user) {
        this.loggedInUser = user;
    }

    @Override
    public User getLoggedUser() {
        verifyUserExists();
        return loggedInUser;
    }

    private void verifyUserExists() {
        if(loggedInUser == null) {
            throw new RuntimeException("No user logged in");
        }
    }
}
