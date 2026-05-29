package dev.ted.tddgame;

import com.github.kkuegler.HumanReadableIdGenerator;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {

    private final HumanReadableIdGenerator idGenerator;

    public LobbyController(HumanReadableIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    static @NonNull LobbyController createForTest(String gameHandle) {
        HumanReadableIdGenerator singletonGenerator = () -> gameHandle;
        return new LobbyController(singletonGenerator);
    }

    @GetMapping("/create-game")
    public String showCreateGameForm(Model model) {
        model.addAttribute("createGameForm", new CreateGameForm(idGenerator.generate()));
        return "create-game";
    }
}
