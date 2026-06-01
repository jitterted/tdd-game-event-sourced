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
    private final CreateGameCommand createGameCommand;

    public LobbyController(HumanReadableIdGenerator idGenerator,
                           CreateGameCommand createGameCommand) {
        this.idGenerator = idGenerator;
        this.createGameCommand = createGameCommand;
    }

    static @NonNull LobbyController createForTest(String gameHandle) {
        HumanReadableIdGenerator singletonGenerator = () -> gameHandle;
        return new LobbyController(singletonGenerator,
                                   new CreateGameCommand());
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
    public String createGameCommand(CreateGameForm createGameForm) {
        createGameCommand.execute("UNKNOWN CREATOR", createGameForm.handle(), createGameForm.title());
        return "redirect:/join-game";
    }

}
