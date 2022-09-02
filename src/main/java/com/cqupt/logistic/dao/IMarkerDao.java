package com.cqupt.logistic.dao;

import com.cqupt.logistic.bean.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarkerDao extends JpaRepository<Marker,Long> {
    Marker findByComment(String origin);
}
