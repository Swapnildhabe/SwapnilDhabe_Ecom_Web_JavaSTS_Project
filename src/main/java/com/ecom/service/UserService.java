package com.ecom.service;



import java.util.List;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;

public interface UserService {
	
	public UserDtls saveUser(UserDtls user);
	
	public UserDtls getUserByEmail(String email);
	
	public List<UserDtls> getUsers(String role);

	public Boolean updateAccountStatus(Integer id, Boolean status);
	
	public void increaseFailAttempt(UserDtls user);
	
	public void UserAccountLock(UserDtls user);
	
	public boolean unlockAccountTimeExpired(UserDtls user);
	
	public void resetAttepmt(int userId);

	public void updateUserResetToken(String email, String resetToken);
	
	public UserDtls getUserByToken(String token);
	
	public UserDtls updateUser(UserDtls user);
	
	
}
