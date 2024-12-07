package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandlerImpl  extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
			
		String email = request.getParameter("username");
		
		UserDtls userDtls = userRepository.findByEmail(email);
		
		if(userDtls.getIsEnable())
		{
			if(userDtls.getAccountNonLocked())
			{
				if(userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME)
				{
					userService.increaseFailAttempt(userDtls);
				}else {
					userService.UserAccountLock(userDtls);
					exception=new LockedException("Your Account is locked !! Failed Attempt 3");
				}
			}else {
				if(userService.unlockAccountTimeExpired(userDtls))
				{
					exception=new LockedException("Your Account  unlocked !! Please try to login");
				}else {					
					exception = new LockedException("Your Account is Locked !! Please try after sometimes");
				}
			}
		}else {
			exception = new LockedException("Your Account is Inactive");
		}
		
		super.setDefaultFailureUrl("/signin?error");
		super.onAuthenticationFailure(request, response, exception);
	}
	
	
}
