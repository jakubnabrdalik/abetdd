package eu.solidcraft.generators

import eu.solidcraft.registration.User
import eu.solidcraft.registration.UserRepository

class UserGenerator {
    private UserRepository userRepositor
    private static int nameNumber = 0

    UserGenerator(UserRepository userRepositor) {
        this.userRepositor = userRepositor
    }

    User saveNew() {
        String email = "user" + nameNumber++
        return saveNew(email)
    }

    User saveNew(email) {
        User user = new User(email, "developer", "Kowalski")
        userRepositor.save(user)
        return user
    }
}
