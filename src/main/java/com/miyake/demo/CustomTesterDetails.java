package com.miyake.demo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.miyake.demo.entities.MyTesterEntity;

public class CustomTesterDetails implements UserDetails {

	private MyTesterEntity tester;

	public CustomTesterDetails(MyTesterEntity tester) {
		this.tester = tester;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return this.tester.getPassword();
	}

	@Override
	public String getUsername() {
		return this.tester.getName();
	}
	
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

	public MyTesterEntity getTester() {
		return tester;
	}

    
}
