package com.miyake.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miyake.demo.entities.UserEntity;
import com.miyake.demo.entities.UserGroupEntity;
import com.miyake.demo.entities.UserRole;
import com.miyake.demo.jsonobject.UserJson;
import com.miyake.demo.repository.UserGroupRepository;
import com.miyake.demo.repository.UserRepository;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserGroupRepository userGroupRepository;
    
    @GetMapping("/userEntities")
    public List<UserEntity> getUsers(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return this.userRepository.findByUsergroup(userDetails.getUser().getUsergroup());
    }
    
    @GetMapping("/password")
    public String password(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "password", required=true) String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        
        UserEntity userEntity = this.userRepository.getById(userDetails.getUser().getId());
        userEntity.setPassword(encodedPassword);
        
        this.userRepository.save(userEntity);
    	return "OK";
    }
    
    @PostMapping("/registerAdministrator")
    public UserEntity registerAdministrator(@RequestBody UserJson user) {
    	UserGroupEntity userGroup = new UserGroupEntity();
    	userGroup.setName(user.groupName);
    	userGroup = this.userGroupRepository.save(userGroup);
    	
    	UserEntity userEntity = new UserEntity();
    	userEntity.setEmail(user.email);
    	userEntity.setFirstName(user.firstName);
    	userEntity.setLastName(user.lastName);
    	
    	userEntity.setRole(UserRole.Administrator);
    	userEntity.setUsergroup(userGroup.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.password);
        userEntity.setPassword(encodedPassword);
        
    	return this.userRepository.save(userEntity);
    }
}
