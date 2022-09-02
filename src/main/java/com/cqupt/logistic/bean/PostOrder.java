package com.cqupt.logistic.bean;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "postOrder")
public class PostOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String customerId;
    @Column(length = 50)
    private String receiverId;
    @Column(length = 50)
    private String goodsId;
    @Column(length = 50)
    private String receiverPhone;
    @Column(length = 50)
    private String driverId;
    @Column(length = 50)
    private String goodsStartSite;
    @Column
    private Date goodsStartTime;
    @Column
    private Date goodsArriveTime;
    @Column(length =50)
    private String status;
    @Column(length = 50)
    private String goodsOrderSite;
    @Column(length = 50)
    private String bills;
    @Column(length = 50)
    private String goodsType;
    @Column(length = 50)
    private Integer goodsSize;
    @Column(length = 50)
    private String goodsDetail;

    public PostOrder() {
    }

    public PostOrder(Integer id, String customerId, String receiverId, String goodsOrderSite, String receiverPhone, String goodsStartSite, Date goodsStartTime, String goodsType, int goodsSize) {
        this.id=id;
        this.customerId = customerId;
        this.receiverId = receiverId;
        this.receiverPhone = receiverPhone;
        this.goodsStartSite = goodsStartSite;
        this.goodsOrderSite=goodsOrderSite;
        this.goodsStartTime = goodsStartTime;
        this.goodsType = goodsType;
        this.goodsSize=goodsSize;
    }

    public PostOrder(int id, String customerId, String goodsId, String goodsStartSite, String status, String goodsOrderSite, String bills, String goodsType, String goodsDetail, Date goodsStartTime) {
        this.id = id;
        this.customerId = customerId;
        this.goodsId = goodsId;
        this.goodsStartSite = goodsStartSite;
        this.goodsOrderSite = goodsOrderSite;
        this.bills = bills;
        this.goodsType = goodsType;
        this.goodsDetail = goodsDetail;
        this.goodsStartTime=goodsStartTime;
        this.status=status;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Integer getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(Integer goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Date getGoodsStartTime() {
        return goodsStartTime;
    }

    public void setGoodsStartTime(Date goodsStartTime) {
        this.goodsStartTime = goodsStartTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsStartSite() {
        return goodsStartSite;
    }

    public void setGoodsStartSite(String goodsStartSite) {
        this.goodsStartSite = goodsStartSite;
    }

    public String getGoodsOrderSite() {
        return goodsOrderSite;
    }

    public void setGoodsOrderSite(String goodsOrderSite) {
        this.goodsOrderSite = goodsOrderSite;
    }

    public String getBills() {
        return bills;
    }

    public void setBills(String bills) {
        this.bills = bills;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

}
