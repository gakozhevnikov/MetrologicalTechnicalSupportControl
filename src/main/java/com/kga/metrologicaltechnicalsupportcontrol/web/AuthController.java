package com.kga.metrologicaltechnicalsupportcontrol.web;

import com.kga.metrologicaltechnicalsupportcontrol.payload.request.LoginRequest;
import com.kga.metrologicaltechnicalsupportcontrol.payload.request.SignupRequest;
import com.kga.metrologicaltechnicalsupportcontrol.payload.response.JWTTokenSuccessResponse;
import com.kga.metrologicaltechnicalsupportcontrol.payload.response.MessageResponse;
import com.kga.metrologicaltechnicalsupportcontrol.security.JWTTokenProvider;
import com.kga.metrologicaltechnicalsupportcontrol.security.SecurityConstants;
import com.kga.metrologicaltechnicalsupportcontrol.services.UserService;
import com.kga.metrologicaltechnicalsupportcontrol.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")// /auth/signin
@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;

    /*@GetMapping("/signin")
    public ModelAndView signIn(Model model, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView("signin");
        //model.addAttribute("greeting", "Hello, welcome");
        //request.setAttribute("action", "Please login");
        modelAndView.addObject();
        return modelAndView;
    }*/


    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
