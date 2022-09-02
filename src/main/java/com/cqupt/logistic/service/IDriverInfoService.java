package com.cqupt.logistic.service;


import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.DriverInfo;
import com.cqupt.logistic.bean.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDriverInfoService {
	
	public Page<DriverInfo> findAllByPage(Pageable pageable);
	
	public boolean addNewDriver(DriverInfo driverInfo);
	
	public boolean updateById(String id, DriverInfo driverInfo);
	
	public DriverInfo findById(String id);
	
	public boolean deleteById(String id);
	
	public List<String> findAllId();

	List<DriverGet> selectGetById(String id);

    List<Marker> getMarker();

    List<String> getMarkerComment();

	boolean addMaker(Marker marker);
}
