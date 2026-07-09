package com.helper.servirce;

import org.springframework.beans.factory.annotation.Autowired;
import com.helper.dto.SignupRequest;
// import com.helper.dto.SignupResponse;
import com.helper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import com.helper.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserDtoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void registerUser(SignupRequest signupRequest){
          
        Users user = modelMapper.map(signupRequest, Users.class);

        userRepository.save(user);
        // return new SignupResponse();
    }

}
