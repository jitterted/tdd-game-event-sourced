package dev.ted.tddgame.jeslib;

import org.jspecify.annotations.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.HashSet;
import java.util.Set;

public interface Event {
    default @NonNull Set<String> tags() {
        Set<String> tagStrings = new HashSet<>();
        for (RecordComponent recordComponent : this.getClass().getRecordComponents()) {
            for (Class<?> anInterface : recordComponent.getType().getInterfaces()) {
                if (anInterface.isAssignableFrom(Tag.class)) {
                    Tag tag;
                    try {
                        tag = (Tag) recordComponent.getAccessor().invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Invocation of record component accessor failed", e);
                    }
                    tagStrings.add(tag.asString());
                }
            }
        }
        return tagStrings;
    }
}
