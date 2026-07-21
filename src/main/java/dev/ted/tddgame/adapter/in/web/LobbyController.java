package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class LobbyController {

    private final GamesAvailableToJoinProjector gamesAvailableToJoinProjector;
    private final EventStore eventStore;

    public LobbyController(GamesAvailableToJoinProjector gamesAvailableToJoinProjector,
                           EventStore eventStore) {
        this.gamesAvailableToJoinProjector = gamesAvailableToJoinProjector;
        this.eventStore = eventStore;
    }

    @GetMapping("/")
    public String redirectToLobby() {
        return "redirect:/lobby";
    }

    @GetMapping("/lobby")
    public String showLobby(Principal principal, Model model) {
        if (isUnregisteredMember(principal)) {
            return "redirect:/register";
        }
        model.addAttribute("availableGames", gamesAvailableToJoinProjector.projection().games());
        return "lobby";
    }

    private boolean isUnregisteredMember(Principal principal) {
        List<StoredEvent> events = eventStore.query(
                new QueryPredicate(MemberRegistered.class,
                                   new Username(principal.getName())));
        return events.isEmpty();
    }

    @PostMapping("/join")
    public String joinGame(Principal principal,
                           @RequestParam("gameHandle") String gameHandle) {
        return "redirect:/game";
    }
}