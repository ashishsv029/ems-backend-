package net.meghasandesham.emsbackend.security;


import lombok.AllArgsConstructor;
import net.meghasandesham.emsbackend.repository.UserRepository;
import net.meghasandesham.emsbackend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exists by Username or Email"));
        Set<GrantedAuthority> authorities = user.getRoles().stream() //This converts the collection of roles associated with the user into a stream for processing.
                .map((role) -> new SimpleGrantedAuthority(role.getName())) //  For each role in the user's roles, a new SimpleGrantedAuthority object is created with the role's name. SimpleGrantedAuthority is a class provided by Spring Security that implements the GrantedAuthority interface.
                .collect(Collectors.toSet()); //The stream of SimpleGrantedAuthority objects is collected into a Set. A Set is used because it automatically handles duplicates, ensuring that each authority is unique.
                //SimpleGrantedAuthority is needed to let know spring security about the roles we have to each user, so that it validated properly
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail, user.getPassword(), authorities
        );
    }
}
