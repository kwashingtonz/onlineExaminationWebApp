package com.onlineExamApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	public Integer getIdByName(String uname) {
		
		Integer uid = userRepository.getIdByName(uname);
		return uid;
	}
	
	public Integer getCountStudents() {
		
		Integer count = userRepository.getCountStudents();
		
		return count;
	}
	
	public List<Users> listStudents() {
        return userRepository.listStudents();
    }

}

