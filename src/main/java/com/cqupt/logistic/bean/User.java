package com.cqupt.logistic.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户表
 * 
 * @author leechuang
 *
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(strategy = "assigned", name = "id")
	@Column(length = 50)
	private String loginId;// 登录ID
	
	@Column(length = 50)
	private String password;

	@Column(length = 50)
	private String username;
	@Column(length = 50)
	private String  vertifyCode;
	@Column(length = 50)
	private String  address;
	@Column(length = 50)
	private String  emails;
	@Column(columnDefinition = "bit(1) default 0")
	private boolean ifOnline;// 是否在线

	@Column(length = 50)
	private String userType;//用户类型
	@Column(length = 50)
	private String phone;//手机号
	@Column(length = 50)
	private String truename;//真实姓名

	public String getVertifyCode() {
		return vertifyCode;
	}

	public void setVertifyCode(String vertifyCode) {
		this.vertifyCode = vertifyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public User() {
		super();
	}

	public User(String loginId, String password, boolean ifOnline, String userType) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.ifOnline = ifOnline;
		this.userType=userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIfOnline() {
		return ifOnline;
	}

	public void setIfOnline(boolean ifOnline) {
		this.ifOnline = ifOnline;
	}

	@Override
	public String toString() {
		return "User [loginId=" + loginId + ", password=" + password + ", ifOnline=" + ifOnline + "]";
	}
	
}
