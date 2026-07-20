package dev.ted.tddgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class TddGameSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails blueUser = createUser("Blue", "blue", "MEMBER");
        UserDetails redUser = createUser("Red", "red", "MEMBER");
        UserDetails greenUser = createUser("Green", "green", "MEMBER");
        UserDetails yellowUser = createUser("Yellow", "yellow", "MEMBER");
        UserDetails blackUser = createUser("Black", "black", "MEMBER");
        UserDetails whiteUser = createUser("White", "white", "MEMBER");

        return new InMemoryUserDetailsManager(blueUser, redUser, greenUser, yellowUser, blackUser, whiteUser);
    }

    private static UserDetails createUser(String username, String password, String... roles) {
        return User.withDefaultPasswordEncoder()
                   .username(username)
                   .password(password)
                   .roles(roles)
                   .build();
    }
}
