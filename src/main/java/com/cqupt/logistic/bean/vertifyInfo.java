package com.cqupt.logistic.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author ACER
 * @Date:2020/12/11
 */
@Entity(name = "vertifyInfo")
public class vertifyInfo {
    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(strategy = "assigned", name = "id")
    @Column(length = 50)
    private String phone;// 登录ID
    @Column(length = 50)
    private String smsCode;
    @Column(length = 50)
    private String setTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSetTime() {
        return setTime;
    }
    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }
}
