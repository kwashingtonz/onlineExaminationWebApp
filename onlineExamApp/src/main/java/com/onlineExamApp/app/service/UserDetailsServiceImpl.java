package com.onlineExamApp.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineExamApp.app.model.Users;
import com.onlineExamApp.app.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users= userRepository.findByEmail(email);
		
		if (users==null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MyUserDetails(users);
	}

}

