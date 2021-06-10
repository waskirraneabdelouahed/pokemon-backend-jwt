package com.nimbleways.pokemon.UserDetails;

import com.nimbleways.pokemon.model.UserEntity;
import com.nimbleways.pokemon.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private UserRepository userRepo;

    public UserDetailsServiceImpl(@Autowired UserRepository userRepo){
        this.userRepo=userRepo;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity Not Found with username: " + username));
        logger.info("loadUserByUsername of UserDetailsServiceImpl: "+String.valueOf(user));
        return UserDetailsImpl.build(user);
    }

}
