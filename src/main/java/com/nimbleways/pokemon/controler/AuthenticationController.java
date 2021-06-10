package com.nimbleways.pokemon.controler;

import com.nimbleways.pokemon.DTO.JwtResponse;
import com.nimbleways.pokemon.DTO.LoginRequest;
import com.nimbleways.pokemon.DTO.MessageResponse;
import com.nimbleways.pokemon.DTO.SignUpRequest;
import com.nimbleways.pokemon.ERoles.ERole;
import com.nimbleways.pokemon.Security.JWT.AuthEntryPoint;
import com.nimbleways.pokemon.Security.JWT.JwtUtils;
import com.nimbleways.pokemon.UserDetails.UserDetailsImpl;
import com.nimbleways.pokemon.model.Role;
import com.nimbleways.pokemon.model.UserEntity;
import com.nimbleways.pokemon.repository.RoleRepository;
import com.nimbleways.pokemon.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("signin")
    public ResponseEntity<?> SignInAuthentication(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("---------------");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("authorities from userDetails for this users: "+userDetails.getAuthorities());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        logger.info("Roles for this users: "+roles);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    //Signing Up
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        //Check if either Username Or Email already exist
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        //Check if there are Roles in RequestBody
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        logger.info("strRoles: "+ strRoles+ " HashSet<roles>: "+roles);

        //If there are none we apply default USER ROLE
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        //We persist the UserEntity in the DB
        user.setRoles(roles);
        userRepository.save(user);
        logger.info("user has been Saved:" + user+ " encoder " + "roles length: " +" " + encoder.getClass());
        return ResponseEntity.ok(new MessageResponse("UserEntity registered successfully!"));
    }


}
