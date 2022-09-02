package com.cqupt.logistic.service.impl;

import com.cqupt.logistic.dao.IMarkerDao;
import com.cqupt.logistic.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ACER
 * @Date:2020/12/20
 */
@Transactional
@Service(value = "routeService")
public class RouteService implements IRouteService {
    @Autowired
    private IMarkerDao markerDao;
    @Override
    public String getMarkerDetail(String origin) {
        return markerDao.findByComment(origin).getSite();
    }
}
