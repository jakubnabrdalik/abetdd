package eu.solidcraft.registration;

import com.google.common.base.Predicate;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class SessionsNotInPredicate implements Predicate<Workshop> {
    private Set<Session> sessions = newHashSet();

    public SessionsNotInPredicate(List<Workshop> myWorkshops) {
        for(Workshop workshop : myWorkshops) {
            sessions.add(workshop.getSession());
        }
    }

    @Override
    public boolean apply(Workshop workshop) {
        return (!sessions.contains(workshop.getSession()) && workshop.howManySlotsAreFree() > 0) ? true : false;
    }
}

