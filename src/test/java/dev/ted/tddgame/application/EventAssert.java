package dev.ted.tddgame.application;

import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.jeslib.Event;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

import java.util.Set;

@SuppressWarnings("UnusedReturnValue")
public class EventAssert extends ObjectAssert<Event> {

    public EventAssert(Event event) {
        super(event);
    }


    public EventAssert containsTagValues(GameHandle gameHandle, MemberId memberId) {
        Set<String> expectedTags = Set.of(gameHandle.asString(),
                                          memberId.asString());

        Assertions.assertThat(actual.tags())
                  .isEqualTo(expectedTags);

        return this;
    }

    public EventAssert isOfType(Class<? extends Event> eventType) {
        return (EventAssert) isExactlyInstanceOf(eventType);
    }
}
