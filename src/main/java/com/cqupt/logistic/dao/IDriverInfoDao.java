package com.cqupt.logistic.dao;


import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.DriverInfo;
import com.cqupt.logistic.bean.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDriverInfoDao extends JpaRepository<DriverInfo, Long> {

	public DriverInfo findById(String id);
	
	@Query(value = "select id from driverinfo")
	public List<String> findAllId();

	@Query(value ="  from driverget where  driverId=?1 and goods_state=?2")
	List<DriverGet> selectGetById(String id,String status);

	@Query(value = "from marker where 1=1")
    List<Marker> queryMarker();
	@Query(value = "select  comment from marker")
    List<String> getMarkerComment();
}
