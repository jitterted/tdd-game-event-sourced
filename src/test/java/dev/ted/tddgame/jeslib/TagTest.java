package dev.ted.tddgame.jeslib;

import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.PlayerId;
import dev.ted.tddgame.domain.Username;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TagTest {

    @Test
    void memberIdUsesMemberPrefixForTag() {
        MemberId memberId = new MemberId(UUID.randomUUID());

        assertThat(memberId.asString())
                .isEqualTo("member:" + memberId.uuid());
    }

    @Test
    void playerIdUsesPlayerPrefixForTag() {
        PlayerId playerId = new PlayerId(UUID.randomUUID());

        assertThat(playerId.asString())
                .isEqualTo("player:" + playerId.uuid());
    }

    @Test
    void usernameUsesUsernamePrefixForTag() {
        Username username = new Username("my_username");

        assertThat(username.asString())
                .isEqualTo("username:" + username.username());
    }

    @Test
    void gameHandleUsesGameHandlePrefixForTag() {
        GameHandle gameHandle = new GameHandle("my_game_handle");

        assertThat(gameHandle.asString())
                .isEqualTo("gamehandle:" + gameHandle.gameHandle());
    }
}