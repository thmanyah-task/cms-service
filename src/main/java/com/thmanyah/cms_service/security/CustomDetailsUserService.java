package com.thmanyah.cms_service.security;


import com.thmanyah.cms_service.entity.Role;
import com.thmanyah.cms_service.entity.User;
import com.thmanyah.cms_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomDetailsUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + userNameOrEmail);
        }

        // Convert roles to GrantedAuthority with ROLE_ prefix
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();

        return new CustomUserDetails(user);
    }



    private Collection<? extends GrantedAuthority> mapRolesToUsers(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }



}
