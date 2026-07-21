package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;

public class RegisterMemberCommand {

    private final EventStore eventStore;

    public RegisterMemberCommand(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public static RegisterMemberCommand createForTest() {
        return new RegisterMemberCommand(new InMemoryEventStore());
    }

    public Result<Event, String> execute(boolean usernameExists,
                                         boolean memberIdExists,
                                         Username username,
                                         MemberId memberId) {
        if (usernameExists) {
            return Result.failure("Username '%s' already exists".formatted(username.username()));
        }
        if (memberIdExists) {
            return Result.failure("MemberID '%s' already exists".formatted(memberId.uuid().toString()));
        }

        Event event = new MemberRegistered(username, memberId);

        eventStore.append(event);

        return Result.success(event);
    }

}
