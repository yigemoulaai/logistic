package com.cqupt.logistic.service;

import com.cqupt.logistic.bean.User;

import java.util.Map;

public interface IUserService {
	
	public Map<?, ?> userLogin(String loginId, String password);
	
	public boolean ifExist(String loginId);
	
	public boolean changePassword(String loginId, String oldPassword, String newPassword);

    boolean addUser(User user);

	public  boolean sendCode(String phone);

	User findById(String loginId);

	boolean updateUser(User user1);
}
