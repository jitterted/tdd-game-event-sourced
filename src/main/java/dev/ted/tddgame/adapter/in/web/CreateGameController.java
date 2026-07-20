package dev.ted.tddgame.adapter.in.web;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import dev.ted.tddgame.application.CreateGameCommand;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CreateGameController {

    private final HumanReadableIdGenerator idGenerator;
    private final CreateGameCommand createGameCommand;

    public CreateGameController(HumanReadableIdGenerator idGenerator,
                                CreateGameCommand createGameCommand) {
        this.idGenerator = idGenerator;
        this.createGameCommand = createGameCommand;
    }

    static @NonNull CreateGameController createForTest(String gameHandle) {
        HumanReadableIdGenerator singletonGenerator = () -> gameHandle;
        return new CreateGameController(singletonGenerator,
                                        CreateGameCommand.createForTest());
    }

    static @NonNull CreateGameController createForTest(CreateGameCommand createGameCommand) {
        return new CreateGameController(
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
        return "redirect:/lobby";
    }

}
