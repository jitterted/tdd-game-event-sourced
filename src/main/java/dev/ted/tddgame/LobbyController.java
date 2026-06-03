package dev.ted.tddgame;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LobbyController {

    private final HumanReadableIdGenerator idGenerator;
    private final CreateGameCommand createGameCommand;

    public LobbyController(HumanReadableIdGenerator idGenerator,
                           CreateGameCommand createGameCommand) {
        this.idGenerator = idGenerator;
        this.createGameCommand = createGameCommand;
    }

    static @NonNull LobbyController createForTest(String gameHandle) {
        HumanReadableIdGenerator singletonGenerator = () -> gameHandle;
        return new LobbyController(singletonGenerator,
                                   CreateGameCommand.createForTest());
    }

    static @NonNull LobbyController createForTest(CreateGameCommand createGameCommand) {
        return new LobbyController(
                new PermutationBasedHumanReadableIdGenerator(),
                createGameCommand);
    }

    @GetMapping("/create-game")
    public String showCreateGameForm(Model model) {
        model.addAttribute("createGameForm",
                           new CreateGameForm(idGenerator.generate(), ""));
        return "create-game";
    }

    @PostMapping("/create-game")
    public String createGameCommand(Principal principal, CreateGameForm createGameForm) {
        createGameCommand.execute(principal.getName(),
                                  createGameForm.gameHandle(),
                                  createGameForm.title());
        return "redirect:/join-game";
    }

}
