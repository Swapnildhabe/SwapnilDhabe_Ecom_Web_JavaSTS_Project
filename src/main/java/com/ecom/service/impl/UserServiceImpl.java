package com.ecom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override 
	public UserDtls saveUser(UserDtls user) {
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		UserDtls saveUser = userRepository.save(user);
		return saveUser;
	}

	@Override
	public UserDtls getUserByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDtls> getUsers(String role) {
		return userRepository.findByRole(role) ;
	}

	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		
		Optional<UserDtls> findByuser = userRepository.findById(id);
		if(findByuser.isPresent())
		{
			UserDtls userDtls = findByuser.get();
			userDtls.setIsEnable(status);
			userRepository.save(userDtls);
			return true;
		}
		return false;
	}

	@Override
	public void increaseFailAttempt(UserDtls user) {
		int attempt =	user.getFailAttempt() + 1;
		user.setFailAttempt(attempt);
		userRepository.save(user);
		
	}

	@Override
	public void UserAccountLock(UserDtls user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
		
	}

	@Override
	public boolean unlockAccountTimeExpired(UserDtls user) {
			
		long lockTime = user.getLockTime().getTime();
		long unlockTime = lockTime+AppConstant.UNLOCK_DURATION_TIME;
		
		long currentTime = System.currentTimeMillis();
		
		if(unlockTime<currentTime)
		{
			user.setAccountNonLocked(true);
			user.setFailAttempt(0);
			userRepository.save(user);
			return true;
		}
		
		return false;
	}

	@Override
	public void resetAttepmt(int userId) {
		// TODO Auto-generated method stub
		
	}


	

}
