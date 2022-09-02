package com.cqupt.logistic.dao;


import com.cqupt.logistic.bean.User;
import com.cqupt.logistic.bean.vertifyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User, Long> {

	public User findByLoginId(String loginId);
	
	@Modifying
	@Query(value = "update user set if_online = ?1 where login_id = ?2", nativeQuery = true)
	public void updateOnline(boolean status, String loginId);

	public  User findByPhone(String phone);
}
