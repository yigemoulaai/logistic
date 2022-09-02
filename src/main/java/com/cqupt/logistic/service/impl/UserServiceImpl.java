package com.cqupt.logistic.service.impl;


import com.cqupt.logistic.bean.User;
import com.cqupt.logistic.bean.vertifyInfo;
import com.cqupt.logistic.dao.IUserDao;
import com.cqupt.logistic.dao.IVertifyInfoDao;
import com.cqupt.logistic.service.IUserService;
import com.cqupt.logistic.util.Enctype;
import com.cqupt.logistic.util.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED)
@Service(value = "userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IVertifyInfoDao vertifyInfoDao;

	@Override
	public Map<?, ?> userLogin(String phone, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();

		vertifyInfo vertifyInfo = vertifyInfoDao.findByPhone(phone);
		long currentTime =System.currentTimeMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long oldDate=0;
		try {
			oldDate=df.parse(df.format(Long.valueOf(vertifyInfo.getSetTime()))).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff=(currentTime-oldDate)/1000/60;
		if (vertifyInfo.getSmsCode() .equals(password)&&diff<=3 ) {
			User user = userDao.findByPhone(phone);
				// 上线
				userDao.updateOnline(true, user.getLoginId());
				result.put("STATUS", "SUCCESS");
				result.put("USER", user);

		} else {
			result.put("STATUS", "ERROR");
		}
		return result;
	}

	@Override
	public boolean ifExist(String loginId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		flag = userDao.findByLoginId(loginId) == null ? false : true;
		return flag;
	}

	@Override
	public boolean changePassword(String loginId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		User user = userDao.findByLoginId(loginId);
		if (Enctype.MD5(oldPassword).equals(user.getPassword())) {
			user.setPassword(Enctype.MD5(newPassword));
			userDao.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean addUser(User user) {
		String loginId="";
		if(user.getUserType()=="客户")
		{
			loginId+="KH"+(int)((Math.random()*9+1)*100000);
		}
		else {
			loginId+="SJ"+(int)((Math.random()*9+1)*100000);
		}
		user.setIfOnline(false);
		user.setLoginId(loginId);
		user.setPassword(Enctype.MD5(user.getPassword()));
		userDao.save(user);
		return true;
	}

	@Override
	public boolean sendCode(String phone) {
		vertifyInfo vertifyInfo=new vertifyInfo();
		int icode=(int)(((Math.random()*9+1)*100000));
		String code=String.valueOf(icode);
		boolean flag=SendSms.sendSms(phone,code);
		if(flag)
		{
			vertifyInfo.setPhone(phone);
			vertifyInfo.setSmsCode(code);
			String time=String.valueOf(new Date().getTime());
			vertifyInfo.setSetTime(time);
			vertifyInfoDao.save(vertifyInfo);
		}
		return flag;
	}

	@Override
	public User findById(String loginId) {
		User user=userDao.findByLoginId(loginId);
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		userDao.save(user);
		return true;
	}


}
