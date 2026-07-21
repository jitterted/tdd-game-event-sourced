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

    private static final MemberId IRRELEVANT_MEMBER_ID = MemberId.createRandom();
    private static final Username IRRELEVANT_USERNAME = new Username("irrelevant");

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

    @Test
    void usernameExists_commandFailsWithMessage() {
        RegisterMemberCommand command = RegisterMemberCommand.createForTest();

        boolean usernameExists = true;
        boolean memberIdExists = false;
        Result<Event, String> result = command.execute(usernameExists,
                                                       memberIdExists,
                                                       new Username("existing-username"),
                                                       IRRELEVANT_MEMBER_ID);

        assertThat(result.isFailure())
                .as("Expected command to fail if username exists")
                .isTrue();
        assertThat(result.failureInfo())
                .isEqualTo("Username 'existing-username' already exists");
    }

    @Test
    void memberIdExists_commandFailsWithMessage() {
        RegisterMemberCommand command = RegisterMemberCommand.createForTest();

        boolean usernameExists = false;
        boolean memberIdExists = true;
        MemberId memberId = MemberId.from("d390396f-bcc3-4fed-870d-f2e9ecb45338");
        Result<Event, String> result = command.execute(usernameExists,
                                                       memberIdExists,
                                                       IRRELEVANT_USERNAME,
                                                       memberId);

        assertThat(result.isFailure())
                .as("Expected command to fail if Member ID exists")
                .isTrue();
        assertThat(result.failureInfo())
                .isEqualTo("MemberID 'd390396f-bcc3-4fed-870d-f2e9ecb45338' already exists");

    }
}