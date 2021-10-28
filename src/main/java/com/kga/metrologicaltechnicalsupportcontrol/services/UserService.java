package com.kga.metrologicaltechnicalsupportcontrol.services;

import com.kga.metrologicaltechnicalsupportcontrol.dto.UserDTO;
import com.kga.metrologicaltechnicalsupportcontrol.model.User;
import com.kga.metrologicaltechnicalsupportcontrol.util.ERole;
import com.kga.metrologicaltechnicalsupportcontrol.exceptions.UserExistException;
import com.kga.metrologicaltechnicalsupportcontrol.payload.request.SignupRequest;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getName());
        user.setLastName(userIn.getLastName());
        user.setUserName(userIn.getUserName());
        user.setCompany(userIn.getCompany());
        user.setJobTitle(userIn.getJobTitle());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.READ);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastname());
        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
