package com.cqupt.logistic.util.Antcore;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName Plan
 * @Description
 * @Author
 * @Date 2020/8/31 0031 17:30
 * @Version 1.0
 **/
public class Plan {

        public static Ant plan(String begin,String[] pass) throws Exception {

        //起始地
        String origin = begin;
        //经过点
        List<String> waypoints = new ArrayList<>();
       for(String x:pass)
       {
           waypoints.add(x);
       }
        //起始位置
        waypoints.add(0,origin);

        //1. 计算各地点的经纬度
        List<LocationEntity> lists = Match.batchQueryLocation(waypoints);

        //起始点放在其实第一位
        LocationEntity locationEntity = new LocationEntity(0, origin);
        lists.add(0,locationEntity);

        // 获取距离矩阵
        Double[][] distances = Match.returnShortCut(lists);
        // 调用蚁群算法，选出最短路径
        // 初始化数据
        PublicFun.g_Distance = distances;
        // 城市数量
        PublicFun.N_CITY_COUNT = lists.size()-1;
        //初始化蚂蚁数量
        PublicFun.N_ANT_COUNT = 2*lists.size();
        Tsp tsp = new Tsp();
        tsp.InitData();
        Ant ant = tsp.m_bestAnt;
       return ant;
//        List<LocationEntity> returnLists = new ArrayList<>(lists.size());
        //计算两点之间距离
     /*   List<LocationEntity> subEntitys = lists.subList(1, lists.size());
        waypoints = subEntitys.stream().map(location->location.getLocation()).collect(Collectors.toList());
//        List<Double> distance = Match.returnDistance(StringUtils.join(waypoints,"|"), origin);

        // 设置排序号
        for (int i = 0; i < ant.m_nPath.length; i++) {
            LocationEntity entity = lists.get(ant.m_nPath[i]);
            entity.setSortNum(i);
//            if(i == 0) {
//                entity.setDistance(0d);
//            }else {
//                entity.setDistance(distance.get(ant.m_nPath[i]-1));
//            }
//            returnLists.add(entity);
        }
        System.out.println("蚂蚁走过最佳的路径："+ant.m_dbPathLength);
*/
    }

}
