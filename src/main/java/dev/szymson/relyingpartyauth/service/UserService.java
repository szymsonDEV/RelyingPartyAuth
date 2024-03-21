package dev.szymson.relyingpartyauth.service;

import dev.szymson.relyingpartyauth.domain.UserDetailsDTO;
import dev.szymson.relyingpartyauth.u2f.CredentialRegistration;
import dev.szymson.relyingpartyauth.u2f.InMemoryRegistrationStorage;
import dev.szymson.relyingpartyauth.domain.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Collection;

/**
 * @author Szymon.Romanowski
 **/
@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final InMemoryRegistrationStorage inMemoryRegistrationStorage;


    public void createUser(UserDTO userDTO){
        boolean userExist = inMemoryUserDetailsManager.userExists(userDTO.getUsername());
        if(userExist) {
            throw new EntityExistsException(String.format("User with username '%s' already exist.", userDTO.getUsername()));
        }

        inMemoryUserDetailsManager.createUser(
                new User(
                        userDTO.getUsername(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        userDTO.getAuthorities()));
    }

    public UserDetailsDTO getUser(String username) {
        UserDetails user = inMemoryUserDetailsManager.loadUserByUsername(username);
        Collection<CredentialRegistration> registrationsByUsername = inMemoryRegistrationStorage.getRegistrationsByUsername(username);

        return UserDetailsDTO.builder()
                .username(user.getUsername())
                .registredCredentials(registrationsByUsername.size())
                .build();
    }
}
