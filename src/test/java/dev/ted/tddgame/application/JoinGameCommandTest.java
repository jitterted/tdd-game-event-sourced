package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.domain.EventFactory;
import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.PlayerJoined;
import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;
import org.junit.jupiter.api.Test;

class JoinGameCommandTest {

    @Test
    void executeJoinGameReturnsSuccessfulPlayerJoinedEvent() {
        EventStore eventStore = new InMemoryEventStore();
        GameHandle gameHandle = new GameHandle("game to be joined");
        eventStore.append(EventFactory.gameCreatedWithHandle(gameHandle));
        MemberRegistered memberRegistered = EventFactory.memberRegistered();
        eventStore.append(memberRegistered);
        JoinGameCommand joinGameCommand = new JoinGameCommand(eventStore);

        Result<Event, String> result = joinGameCommand.execute(
                memberRegistered.memberId(),
                gameHandle);

        EventResultAssert.assertThat(result)
                         .succeeded()
                         .isOfType(PlayerJoined.class)
                         .containsTagValues(gameHandle, memberRegistered.memberId());
    }
}