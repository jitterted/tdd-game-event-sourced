package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.Members;
import dev.ted.tddgame.domain.Username;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LobbyController {

    private final GamesAvailableToJoinProjector gamesAvailableToJoinProjector;
    private final Members members;

    public LobbyController(GamesAvailableToJoinProjector gamesAvailableToJoinProjector,
                           Members members) {
        this.gamesAvailableToJoinProjector = gamesAvailableToJoinProjector;
        this.members = members;
    }

    @GetMapping("/")
    public String redirectToLobby() {
        return "redirect:/lobby";
    }

    @GetMapping("/lobby")
    public String showLobby(Principal principal, Model model) {
        if (members.isUnregisteredMember(new Username(principal.getName()))) {
            return "redirect:/register";
        }
        model.addAttribute("availableGames", gamesAvailableToJoinProjector.projection().games());
        return "lobby";
    }

    @PostMapping("/join")
    public String joinGame(Principal principal,
                           @RequestParam("gameHandle") String gameHandle) {
        // games.joinGame(members.idFor(new Username(principal.getName())), new GameHandle(gameHandle))
        return "redirect:/game";
    }
}