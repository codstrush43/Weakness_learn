package com.helper.servirce;

import org.springframework.beans.factory.annotation.Autowired;

// import com.helper.dto.SignupResponse;

import com.helper.repository.UserRepository;

import org.modelmapper.ModelMapper;
import com.helper.entity.Users;
import com.helper.dto.codeforcesDto.CodeforcesUserInfo;
import com.helper.dto.signup.SignupRequest;
import com.helper.entity.Codeforces;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserDtoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(SignupRequest signupRequest,CodeforcesUserInfo codeforcesUserInfo){
          
        Users user = modelMapper.map(signupRequest, Users.class);
        Codeforces codeforces=modelMapper.map(codeforcesUserInfo,Codeforces.class);
        
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        codeforces.setUser(user);
        user.setCodeforces(codeforces);
        userRepository.save(user);
        // return new SignupResponse();
    }

    public Users findUserByEmail(String email)
    {
        Users user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    public boolean isPasswordMatch(String rawPassword,String encodedPassword)
    {
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

}
