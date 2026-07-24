package dev.ted.tddgame.application;

import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings("UnusedReturnValue")
public class EventResultAssert extends AbstractAssert<EventResultAssert, Result<Event, String>> {
    protected EventResultAssert(Result<Event, String> result) {
        super(result, EventResultAssert.class);
    }

    public static EventResultAssert assertThat(Result<Event, String> result) {
        return new EventResultAssert(result);
    }

    public EventAssert succeeded() {
        isNotNull();
        if (!actual.isSuccess()) {
            failWithMessage("Expected Result to be successful, but it failed with '%s'",
                            actual.failureInfo());
        }
        return new EventAssert(actual.successValue());
    }

}

