package com.cqupt.logistic.controller;


import com.cqupt.logistic.bean.Marker;
import com.cqupt.logistic.service.IDriverInfoService;
import com.cqupt.logistic.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/marker")
@RestController
@CrossOrigin
@ControllerAdvice
public class markerController extends  ReturnType {
    @Autowired
     private IDriverInfoService driverInfoService;

    /*
    *
    * 所有仓库数据*/
    @RequestMapping(value = "/selectAllMarker",method = RequestMethod.GET)
        private Map<String,Object> getAllMarker()
        {
            List<Marker> list = driverInfoService.getMarker();
            Map<String,Object> resultMap = new HashMap<String,Object>();

            resultMap.put("code",200);

            resultMap.put("msg","");

            resultMap.put("count","1000");

            resultMap.put("data",list);

            return resultMap;
        }

    @RequestMapping(value = "/addMarker",method = RequestMethod.POST)
    private Map<?,?> addMaker(Marker marker)
    {
        boolean flag;
        Map<String,Object> map=new HashMap<>();
        String originDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoding/v3/?output=json&ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&address="+marker.getSite());
        com.alibaba.fastjson.JSONObject jsonObjectOri = com.alibaba.fastjson.JSONObject.parseObject(originDouble);
        String oriLng = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lng");
        String oriLat = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lat");

        marker.setLatitude(oriLng);
        marker.setLongitude(oriLat);
        flag= driverInfoService.addMaker(marker);
        if (flag== false) {
        map.put("STATUS",ERROR);
        }
        else map.put("STATUS",SUCCESS);
        return map;
    }
}
