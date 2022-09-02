package com.cqupt.logistic.controller;

import com.cqupt.logistic.bean.CustomerInfo;
import com.cqupt.logistic.bean.PostOrder;
import com.cqupt.logistic.service.ICustomerInfoService;
import com.cqupt.logistic.service.IDriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ACER
 * @Date:2020/12/17
 */
@RestController
@CrossOrigin
@ControllerAdvice
public class CustomerController extends ReturnType {
    @Autowired
    private ICustomerInfoService customerInfoService;
    /*
    *根據ID查找客戶信息
    * */
    @RequestMapping(value = "/selectCusOrderByCode/{customerCode}", method = RequestMethod.GET)
    public Map<?,?> selectCusById(@PathVariable("customerCode") String customerCode){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        CustomerInfo customerInfo =  customerInfoService.selectCusById(customerCode);
        resultMap.put("code",200);
        resultMap.put("msg","");
        resultMap.put("count","1000");
        resultMap.put("data",customerInfo);
        return resultMap;

    }
    /*
    * 更新客户信息
    * */
    @RequestMapping(value = "/updateCus", method = RequestMethod.POST, produces = "application/json")
    public Map<?,?> addNewCus(CustomerInfo customer) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
         customerInfoService.save(customer);

        resultMap.put("STATUS",SUCCESS);
        return resultMap;
    }

}
