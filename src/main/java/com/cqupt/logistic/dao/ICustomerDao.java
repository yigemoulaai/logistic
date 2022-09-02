package com.cqupt.logistic.dao;

import com.cqupt.logistic.bean.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author ACER
 * @Date:2020/12/17
 */
public interface ICustomerDao extends JpaRepository<CustomerInfo, Long> {

    CustomerInfo findByCustomerCode(String customerCode);
}
