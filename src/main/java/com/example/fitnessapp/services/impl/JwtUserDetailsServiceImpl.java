package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {
    private final UserEntityRepository repository;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(repository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(username)), JwtUser.class);
    }
}
