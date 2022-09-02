package com.cqupt.logistic.service.impl;


import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.DriverInfo;
import com.cqupt.logistic.bean.Marker;
import com.cqupt.logistic.bean.User;
import com.cqupt.logistic.dao.IDriverInfoDao;
import com.cqupt.logistic.dao.IMarkerDao;
import com.cqupt.logistic.dao.IUserDao;
import com.cqupt.logistic.service.IDriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Transactional
@Service(value = "driverInfoService")
public class DriverInfoServiceImpl implements IDriverInfoService {
	
	@Autowired
	private IDriverInfoDao driverInfoDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IMarkerDao markerDao;
	@Override
	public Page<DriverInfo> findAllByPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return driverInfoDao.findAll(pageable);
	}

	@Override
	public boolean addNewDriver(DriverInfo driverInfo) {
		/*
		driverInfo.setState("空闲");
		String driverCode = "SJ";
		
		while (true) {
			driverCode += randomCode();
			if (driverInfoDao.findById(driverCode) == null) {
				break;
			}
		}
		driverInfo.setId(driverCode);
		try {
			
			// 添加用户信息表
			User user = new User(driverCode, "E10ADC3949BA59ABBE56E057F20F883E", false,"司机");
			userDao.save(user);

			// 添加用户与组情况
			String department = "司机组";
			int groupId = groupDao.findByGroupName(department).getId();
			UserWithGroup userWithGroup = new UserWithGroup();
			userWithGroup.setGroupId(groupId);
			userWithGroup.setUserId(driverCode);
			userWithGroupDao.save(userWithGroup);

			driverInfoDao.save(driverInfo);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("司机信息表 | 用户信息表 | 用户与组表 插入失败");
			return false;			
		}*/
		return true;
	}

	@Override
	public boolean deleteById(String id) {
		/*// TODO Auto-generated method stub
		DriverInfo driverInfo = driverInfoDao.findById(id);
		User user = userDao.findByLoginId(id);
		//UserWithGroup userWithGroup = userWithGroupDao.findByUserId(id);
		try {
			driverInfoDao.delete(driverInfo);
			userDao.delete(user);
			//userWithGroupDao.delete(userWithGroup);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("司机信息 | 用户表 | 用户与组表 删除失败");
			return false;
		}*/
		return true;
	}

	@Override
	public boolean updateById(String id, DriverInfo driverInfo) {
		/*DriverInfo oldDriverInfo = driverInfoDao.findById(id);
		driverInfo.setState(oldDriverInfo.getState());
		
		try {
			driverInfoDao.save(driverInfo);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("司机信息更新失败");
			return false;
		}*/
		return true;
	}

	@Override
	public DriverInfo findById(String id) {
		return driverInfoDao.findById(id);
	}
	
	private String randomCode() {
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}

	@Override
	public List<String> findAllId() {
		// TODO Auto-generated method stub
		return driverInfoDao.findAllId();
	}

	@Override
	public List<DriverGet> selectGetById(String id) {

		return driverInfoDao.selectGetById(id,"完成评价");
	}

	@Override
	public List<Marker> getMarker() {
		return driverInfoDao.queryMarker();
	}

	@Override
	public List<String> getMarkerComment() {
		return driverInfoDao.getMarkerComment();
	}

	@Override
	public boolean addMaker(Marker marker) {
		try {
			markerDao.save(marker);
			System.out.println("货运回执单添加成功");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("货运回执单删除失败");
			return false;
		}
	}

}
