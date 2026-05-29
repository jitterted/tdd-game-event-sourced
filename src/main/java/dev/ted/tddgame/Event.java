package dev.ted.tddgame;

public interface Event {
    Long eventSequence(); // could be Optional<Long>, but should never be null when it's actually read (by downstream consumers, such as Projectors or Processors)
}
