package dev.ted.tddgame.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LobbyController {

    private final GamesAvailableToJoinProjector gamesAvailableToJoinProjector;

    public LobbyController(GamesAvailableToJoinProjector gamesAvailableToJoinProjector) {
        this.gamesAvailableToJoinProjector = gamesAvailableToJoinProjector;
    }

    @GetMapping("/")
    public String redirectToLobby() {
        return "redirect:/lobby";
    }

    @GetMapping("/lobby")
    public String showLobby(Principal principal, Model model) {
        model.addAttribute("availableGames", gamesAvailableToJoinProjector.projection().games());
        return "lobby";
    }

    @PostMapping("/join")
    public String joinGame(@RequestParam("gameHandle") String gameHandle) {
        return "redirect:/game";
    }
}