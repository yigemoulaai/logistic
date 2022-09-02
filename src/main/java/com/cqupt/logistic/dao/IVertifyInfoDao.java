package com.cqupt.logistic.dao;

import com.cqupt.logistic.bean.vertifyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author ACER
 * @Date:2020/12/11
 */
public interface IVertifyInfoDao extends JpaRepository<vertifyInfo, Long> {

    vertifyInfo findByPhone(String loginId);
}
