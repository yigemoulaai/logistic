package com.cqupt.logistic.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqupt.logistic.bean.Route;


import java.util.*;

public class CalculateDisUtil {

    //origin:出发地地名，如:余杭区
    //destination:目的地地名，如:黄浦区
    //返回两地的行车距离，如:234.56公里
    public static String distance(String origin, String destination)
    {
        Map<String, String> params = new HashMap<>();
        String originDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoding/v3/?output=json&ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&address="+origin);
        String desDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoding/v3/?output=json&ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&address="+destination);

        JSONObject jsonObjectOri = JSONObject.parseObject(originDouble);
        JSONObject jsonObjectDes = JSONObject.parseObject(desDouble);
        String oriLng = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lng");
        String oriLat = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lat");
        System.out.println(oriLat+"|"+oriLng);
        String desLng = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lng");
        String desLat = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lat");
        System.out.println(desLat+"|"+desLng);
        params.put("output","json");
        params.put("ak","NtqUN3AZe4pdGndy6skDQp3AK276CUjT");
        params.put("origins",oriLat+","+oriLng+"|"+oriLat+","+oriLng);
        params.put("destinations",desLat+","+desLng+"|"+desLat+","+desLng);

        String result = HttpClientUtil.doGet("http://api.map.baidu.com/routematrix/v2/driving", params);
        JSONArray jsonArray = JSONObject.parseObject(result).getJSONArray("result");
        String distanceString = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");
        return distanceString;
    }




    public  static  List<Route> logisticsDirection(String origin, String destination)
    {
        Map<String, String> params = new HashMap<>();
        String originDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoding/v3/?output=json&ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&address="+origin);
        String desDouble = HttpClientUtil.doGet("http://api.map.baidu.com/geocoding/v3/?output=json&ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&address="+destination);
        JSONObject jsonObjectOri = JSONObject.parseObject(originDouble);
        JSONObject jsonObjectDes = JSONObject.parseObject(desDouble);
        String oriLng = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lng");
        String oriLat = jsonObjectOri.getJSONObject("result").getJSONObject("location").getString("lat");
        System.out.println(oriLat+"|"+oriLng);
        String desLng = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lng");
        String desLat = jsonObjectDes.getJSONObject("result").getJSONObject("location").getString("lat");
        System.out.println(desLat+"|"+desLng);
        params.put("key","702551f6915cdefab787722150d62dc6");
        params.put("origin",oriLng+","+oriLat);
        params.put("destination",desLng+","+desLat);
        params.put("strategy","5");
        params.put("size","3");
        params.put("province","鄂");
        params.put("number","M15263");
        params.put("cartype","0");
        //{"status":0,"result":{"location":{"lng":113.44960935856369,"lat":30.33358775114635},"precise":0,"confidence":20,"comprehension":100,"level":"城市"}}
      //  String result = HttpClientUtil.doGet("http://api.map.baidu.com/logistics_direction/v1/truck?ak=NtqUN3AZe4pdGndy6skDQp3AK276CUjT&origin="+oriLat+","+oriLng+"&destination="+desLat+","+desLng);
        String result=HttpClientUtil.doGet("https://restapi.amap.com/v4/direction/truck",params);
        JSONObject jsonObjectRoute = JSONObject.parseObject(result);
        JSONArray jsonArray = jsonObjectRoute.getJSONObject("data").getJSONObject("route").getJSONArray("paths");
        JSONArray routeArray=jsonArray.getJSONObject(0).getJSONArray("steps");
        List<Route> list=new ArrayList<>();
        for(int i=0;i<routeArray.size();i++)
        {
            JSONObject jsonObject1 = routeArray.getJSONObject(i);
            Route route=new Route();
            route.setAction(jsonObject1.getString("action"));
            route.setDuration(jsonObject1.getString("duration"));
            route.setAssistant_action(jsonObject1.getString("assistant_action"));
            route.setDistance(jsonObject1.getString("distance"));
            route.setInstruction(jsonObject1.getString("instruction"));
            route.setRoad(jsonObject1.getString("road"));
            list.add(route);
        }
        return list;

    }
}
