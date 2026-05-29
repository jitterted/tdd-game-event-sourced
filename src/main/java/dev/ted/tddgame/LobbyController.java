package dev.ted.tddgame;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    static @NonNull LobbyController createForTest() {
        return new LobbyController(new PermutationBasedHumanReadableIdGenerator());
    }

    @GetMapping("/create-game")
    public String showCreateGameForm(Model model) {
        model.addAttribute("createGameForm", new CreateGameForm(idGenerator.generate()));
        return "create-game";
    }

    @PostMapping("/create-game")
    public String createGameCommand(CreateGameForm createGameForm) {
        return "redirect:/join-game";
    }

}
