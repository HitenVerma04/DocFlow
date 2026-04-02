package com.hiten.docflow.security;

import com.hiten.docflow.entity.User;
import com.hiten.docflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * UserDetailsServiceImpl
 *
 * Spring Security needs this to load a user from the database
 * during login to check credentials.
 *
 * Think of it as Spring Security asking:
 * "Hey, look up this username from your DB for me."
 * We implement loadUserByUsername() to do exactly that.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Spring Security's built-in User object (different from our Entity User!)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()  // authorities/roles (we'll add these in Module 10)
        );
    }
}
