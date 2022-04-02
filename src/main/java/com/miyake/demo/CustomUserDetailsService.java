package com.miyake.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.entities.UserEntity;
import com.miyake.demo.repository.MyTesterRepository;
import com.miyake.demo.repository.UserRepository;
 
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;
     
    @Autowired
    private MyTesterRepository testerRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username);
        if (user == null) {
        	MyTesterEntity myTester = testerRepo.findByName(username);
        	if (myTester == null) {
        		throw new UsernameNotFoundException("User not found");
        	}
        	return new CustomTesterDetails(myTester);
        }
        return new CustomUserDetails(user);
    }
 
}