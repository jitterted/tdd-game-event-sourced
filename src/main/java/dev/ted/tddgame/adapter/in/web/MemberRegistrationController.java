package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.RegisterMemberCommand;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.Username;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
class MemberRegistrationController {

    private final RegisterMemberCommand registerMemberCommand;

    public MemberRegistrationController(RegisterMemberCommand registerMemberCommand) {
        this.registerMemberCommand = registerMemberCommand;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("memberRegistrationForm",
                           new MemberRegistrationForm(UUID.randomUUID().toString()));
        return "register-member";
    }

    @PostMapping("/register")
    public String registerNewMember(Principal principal, MemberRegistrationForm memberRegistrationForm) {
        MemberId memberId = memberRegistrationForm.memberId();
        String username = principal.getName();
        registerMemberCommand.execute(false, false, Username.of(username), memberId);
        return "redirect:/lobby";
    }

}
