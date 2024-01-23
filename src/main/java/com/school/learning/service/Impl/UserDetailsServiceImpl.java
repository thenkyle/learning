package com.school.learning.service.Impl;

import com.school.learning.entity.AppUser;
import com.school.learning.entity.AppUserDetails;
import com.school.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = this.userRepository.findByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException(username+" 無此使用者");
        }
        return new AppUserDetails(user);
    }

}
