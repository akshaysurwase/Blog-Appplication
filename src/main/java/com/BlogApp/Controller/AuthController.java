package com.BlogApp.Controller;

import com.BlogApp.Entity.Role;
import com.BlogApp.Entity.User;
import com.BlogApp.Payload.JwtAuthResponse;
import com.BlogApp.Payload.LoginDto;
import com.BlogApp.Payload.SignUpDto;
import com.BlogApp.Repository.RoleRepository;
import com.BlogApp.Repository.UserRepository;
import com.BlogApp.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){

        System.out.println(loginDto);
       Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Get token from TokenProvider
        String token= tokenProvider.generateToken(authentication);
        System.out.println(token);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        //add a check for username exist in Database
        System.out.println(signUpDto);
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("UserName is Already Taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is Already Taken", HttpStatus.BAD_REQUEST);
        }

        // Create User Object
        User user= new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


        Role roles= roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);


        return new ResponseEntity<>("user Registered Successfully", HttpStatus.OK);

    }

}
