package com.cqupt.logistic.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author ACER
 * @Date:2020/12/11
 */
public class SendSmsTest {

    @Test
    public void sendSms() {
        new SendSms().sendSms("15926096469","123456");
    }
}