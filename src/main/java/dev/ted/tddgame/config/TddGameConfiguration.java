package dev.ted.tddgame.config;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import dev.ted.tddgame.adapter.in.web.GamesAvailableToJoinProjector;
import dev.ted.tddgame.application.CreateGameCommand;
import dev.ted.tddgame.application.RegisterMemberCommand;
import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
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
    RegisterMemberCommand supplyRegisterMemberCommand(EventStore eventStore) {
        return new RegisterMemberCommand(eventStore);
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
