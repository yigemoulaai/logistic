package com.cqupt.logistic.service;

import com.cqupt.logistic.bean.CustomerInfo;
import com.cqupt.logistic.bean.PostOrder;

/**
 * @Author ACER
 * @Date:2020/12/17
 */
public interface ICustomerInfoService {
    CustomerInfo selectCusById(String customerCode);
    void save(CustomerInfo customer);

}
