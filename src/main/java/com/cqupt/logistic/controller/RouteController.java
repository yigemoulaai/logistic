package com.cqupt.logistic.controller;


import com.cqupt.logistic.bean.Route;
import com.cqupt.logistic.service.IRouteService;
import com.cqupt.logistic.util.CalculateDisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/route")
@CrossOrigin
@ControllerAdvice
public class RouteController extends ReturnType {
	@Autowired
	private IRouteService routeService;

	@RequestMapping(value = "/getNavigation/{origin}/{des}", method = RequestMethod.GET)
	public Map<String,Object> getNavigation(@PathVariable("origin") String origin, @PathVariable("des") String des){
		String originDetail=routeService.getMarkerDetail(origin);
		String desDetail=routeService.getMarkerDetail(des);
		List<Route> routes= CalculateDisUtil.logisticsDirection(originDetail,desDetail);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code",200);

		resultMap.put("msg","");

		resultMap.put("count","1000");

		resultMap.put("data",routes);


		return resultMap;
	}

}
