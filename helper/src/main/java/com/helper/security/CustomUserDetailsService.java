package com.helper.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.helper.repository.UserRepository;
import com.helper.entity.Users;
import com.helper.Exception.GeneratingException.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =(Users)userRepository.findByEmail(username).orElse(null);
        if(user==null)
        {
            throw new UserNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(user);
    }


}
