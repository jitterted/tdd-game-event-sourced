package dev.ted.tddgame;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TagTest {

    @Test
    void memberIdUsesMemberPrefixForTag() {
        MemberId memberId = new MemberId(UUID.randomUUID());

        assertThat(memberId.toTag())
                .isEqualTo("member:" + memberId.uuid());
    }

    @Test
    void playerIdUsesPlayerPrefixForTag() {
        PlayerId playerId = new PlayerId(UUID.randomUUID());

        assertThat(playerId.toTag())
                .isEqualTo("player:" + playerId.uuid());
    }

    @Test
    void usernameUsesUsernamePrefixForTag() {
        Username username = new Username("my_username");

        assertThat(username.toTag())
                .isEqualTo("username:" + username.username());
    }

    @Test
    void gameHandleUsesGameHandlePrefixForTag() {
        GameHandle gameHandle = new GameHandle("my_game_handle");

        assertThat(gameHandle.toTag())
                .isEqualTo("gamehandle:" + gameHandle.gameHandle());
    }
}