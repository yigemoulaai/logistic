package com.cqupt.logistic.controller;


import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.DriverInfo;
import com.cqupt.logistic.bean.Marker;
import com.cqupt.logistic.service.IDriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/driverInfo")
@RestController
@CrossOrigin
@ControllerAdvice
public class DriverInfoController extends ReturnType {
	
	@Autowired
	private IDriverInfoService driverInfoService;

	/*
	* 添加一个司机的详情信息
	* */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<?,?> addNewDriver(DriverInfo driverInfo) {
		
		System.out.println(driverInfo);
		Map<String,Object> map=new HashMap<>();
		boolean flag = false;
		flag = driverInfoService.addNewDriver(driverInfo);
		if (!flag) {
			map.put("STATUS",ERROR);
		}
		else {
			map.put("STATUS",SUCCESS);
		}
		return map;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") String id) {
		boolean flag = false;
		flag = driverInfoService.deleteById(id);
		if (!flag) {
			return ERROR;
		}
		return SUCCESS;
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") String id, DriverInfo driverInfo) {
		
		boolean flag = false;
		flag = driverInfoService.updateById(id, driverInfo);
		if (!flag) {
			return ERROR;
		}
		return SUCCESS;
	}



	@RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
	public DriverInfo selectById(@PathVariable("id") String id) {
		DriverInfo driverInfo = driverInfoService.findById(id);
		return driverInfo;
	}

	//查询出该用户的货运薪资信息
	@RequestMapping(value = "/selectGetById/{id}",method = RequestMethod.GET)
	public Map<String,Object> selectGetById(@PathVariable("id") String id)
	{

		List<DriverGet> list=driverInfoService.selectGetById(id);
		if(list==null)
		{
			System.out.println("ok");
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();

		resultMap.put("code",200);

		resultMap.put("msg","");

		resultMap.put("count","1000");

		resultMap.put("data",list);
		return resultMap;

	}

	@RequestMapping(value = "/selectAllId", method = RequestMethod.GET)
	public List<String> selectAllId() {
		List<String> list = driverInfoService.findAllId();
		return list;
	}
	/*
	* 查询地图上的标记
	*
	* */
	@RequestMapping(value = "/getMarker",method = RequestMethod.GET)
	public List<Marker> getMarker()
	{
		List<Marker> list = driverInfoService.getMarker();
		return list;
	}
	//得到货舱地点的详情
	@RequestMapping(value = "/getMarkerComment",method = RequestMethod.GET)
	public List<String> getMarkerComment()
	{
		List<String> list = driverInfoService.getMarkerComment();
		return list;
	}
}
