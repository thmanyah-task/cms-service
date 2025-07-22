package com.thmanyah.cms_service.service.serviceImpl;

import com.thmanyah.cms_service.dto.JwtAuthResponse;
import com.thmanyah.cms_service.dto.SignIn;
import com.thmanyah.cms_service.dto.SignUp;
import com.thmanyah.cms_service.entity.Role;
import com.thmanyah.cms_service.entity.User;
import com.thmanyah.cms_service.enumeration.UserRole;
import com.thmanyah.cms_service.exception.ValidationException;
import com.thmanyah.cms_service.repository.RoleRepository;
import com.thmanyah.cms_service.repository.UserRepository;
import com.thmanyah.cms_service.security.JwtTokenProvider;
import com.thmanyah.cms_service.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Long signUp(SignUp signup) {
        if (signup.getName() == null || signup.getName().isEmpty()){
            throw new ValidationException("Name Cannot Be Null");
        }
        if (signup.getUserName() == null || signup.getUserName().isEmpty()){
            throw new ValidationException("UserName Cannot Be Null");
        }
        if (signup.getEmail() == null || signup.getEmail().isEmpty()){
            throw new ValidationException("Email Cannot Be Null");
        }
        if (signup.getPassword() == null || signup.getPassword().isEmpty()){
            throw new ValidationException("Password Cannot Be Null");
        }
        if(userRepository.existsByUserName(signup.getUserName())) {
            throw new ValidationException("This username is already Existed") ;
        }
        if(userRepository.existsByEmail(signup.getEmail())) {
            throw new ValidationException("This email is already Existed");
        }
        User user = new User();
        user.setEmail(signup.getEmail());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        user.setUserName(signup.getUserName());
        user.setCreatedDate(LocalDateTime.now());
        List<Role> roles = roleRepository.findByNameIn(List.of(UserRole.CONTENT_MANAGER.toString(),UserRole.CONTENT_EDITOR.toString()));
        if (roles.isEmpty()){
            throw new ValidationException("There Is No Role Existed");
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public JwtAuthResponse signIn(SignIn signin) {
        if (signin.getUserName() == null || signin.getUserName().isEmpty()){
            throw new ValidationException("Username Cannot Be Null");
        }
        if (signin.getPassword() == null || signin.getPassword().isEmpty()){
            throw new ValidationException("Password Cannot Be Null");
        }
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(signin.getUserName()
                        , signin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        JwtAuthResponse auth = new JwtAuthResponse(token);
        auth.setAccessToken(token);
        return  auth;
    }
}
