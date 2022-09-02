package com.cqupt.logistic.service.impl;

import com.cqupt.logistic.bean.CustomerInfo;
import com.cqupt.logistic.bean.PostOrder;
import com.cqupt.logistic.dao.ICustomerDao;
import com.cqupt.logistic.service.ICustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ACER
 * @Date:2020/12/17
 */
@Service(value = "customerInfoService")
public class CustomerInfoService implements ICustomerInfoService {
    @Autowired
    private ICustomerDao customerDao;
    @Override
    public CustomerInfo selectCusById(String customerCode) {
        return customerDao.findByCustomerCode(customerCode);
    }

    @Override
    public void save(CustomerInfo customer) {
        customerDao.save(customer);
    }


}
