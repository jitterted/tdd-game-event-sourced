package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class RegisterMemberCommandTest {

    @Test
    void usernameNorMemberIdAlreadyRegistered_memberRegisteredEventGenerated() {
        EventStore eventStore = new InMemoryEventStore();
        RegisterMemberCommand command = new RegisterMemberCommand(eventStore);

        Username username = new Username("username");
        MemberId memberId = MemberId.createRandom();
        Result<Event, String> result = command.execute(false, false, username, memberId);

        assertThat(result.isSuccess())
                .isTrue();

        MemberRegistered memberRegistered = new MemberRegistered(username, memberId);
        assertThat(result.value())
                .isEqualTo(memberRegistered);
        assertThat(eventStore.query(
                new QueryPredicate(
                        MemberRegistered.class,
                        Set.of(username, memberId))))
                .extracting(StoredEvent::payload)
                .containsExactly(memberRegistered);
    }
}