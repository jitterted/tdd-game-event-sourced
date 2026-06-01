package dev.ted.tddgame;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TddGameConfiguration {

    @Bean
    HumanReadableIdGenerator gameHandleIdGenerator() {
        return new PermutationBasedHumanReadableIdGenerator();
    }

    @Bean
    CreateGameCommand supplyCreateGameCommand(EventStore eventStore) {
        return new CreateGameCommand(eventStore);
    }

    @Bean
    EventStore eventStore() {
        return new InMemoryEventStore();
    }

    @Bean
    GamesAvailableToJoinProjector gamesAvailableToJoinProjector(EventStore eventStore) {
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        eventStore.subscribe(projector);
        return projector;
    }
}
