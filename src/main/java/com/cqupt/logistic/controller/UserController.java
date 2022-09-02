package com.cqupt.logistic.controller;

import com.cqupt.logistic.bean.User;
import com.cqupt.logistic.service.IUserService;
import com.cqupt.logistic.util.SendSms;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@ControllerAdvice
public class UserController extends ReturnType {
	
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public Map<?, ?> login(String phone, String vertifyCode) {
		
		Map<?, ?> result = userService.userLogin(phone, vertifyCode);
		return result;
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.PUT)
	public String change(String loginId, String oldPassword, String newPassword) {
		
		System.out.println(loginId + " " + oldPassword + " " + newPassword);
		boolean flag = false;
		flag = userService.changePassword(loginId, oldPassword, newPassword);
		if (!flag) {
			return ERROR;
		}
		return SUCCESS;
	}
	@RequestMapping(value="/addUser",method = RequestMethod.POST,produces = "application/json")
	public Map<?, ?> addUser(User user){
		Map<String, String> map=new HashMap<>();
		boolean flag;
		flag= userService.addUser(user);
		if (flag== false) {
			map.put("STATUS", "ERROR");
		}
		else
			map.put("STATUS", "SUCCESS");
		return map;
	}
	@RequestMapping(value="/sendCode",method = RequestMethod.GET,produces = "application/json")
	public Map<?, ?> sendCode(String phone){
		Map<String, String> map=new HashMap<>();
		boolean flag=userService.sendCode(phone);
		if(flag)map.put("STATUS", "SUCCESS");
		else map.put("STATUS", "ERROR");
		return map;
	}
	@RequestMapping(value="/updateUser",method = RequestMethod.POST,produces = "application/json")
	public Map<?, ?> updateUser(User user){
		Map<String, String> map=new HashMap<>();
		boolean flag;
		User user1= userService.findById(user.getLoginId());
		user1.setAddress(user.getAddress());
		user1.setEmails(user.getEmails());
		flag=userService.updateUser(user1);
		if (flag== false) {
			map.put("STATUS", "ERROR");
		}
		else
			map.put("STATUS", "SUCCESS");
		return map;
	}

	@RequestMapping(value="/getUserInfo/{id}",method = RequestMethod.GET,produces = "application/json")
	public Map<?, ?> updateUser(@PathVariable("id") String id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		User user=userService.findById(id);
		resultMap.put("code",200);
		resultMap.put("msg","");
		resultMap.put("count","1000");
		resultMap.put("data",user);
		return resultMap;
	}

}


