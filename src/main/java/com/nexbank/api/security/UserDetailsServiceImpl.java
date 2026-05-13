package com.nexbank.api.security;
import com.nexbank.api.domain.User;
import com.nexbank.api.exception.UserNotFoundException;
import com.nexbank.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.debug("User not found with email: {}", username);
                    return new UsernameNotFoundException("Invalid credentials");
                });

        log.debug("User {} loaded successfully", username);
        return new UserPrincipal(user);

    }
}
