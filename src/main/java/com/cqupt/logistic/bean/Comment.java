package com.cqupt.logistic.bean;

import javax.persistence.*;

/**
 * @Author ACER
 * @Date:2020/12/19
 */
@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(length = 50)
    private String goodsId;
    @Column(length = 50)
    private String driverId;
    @Column(length = 50)
    private String customerId;
    @Column(length = 50)
    private String logService;
    @Column(length = 50)
    private String serviceAtt;
    @Column(length = 50)
    private String geneService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLogService() {
        return logService;
    }

    public void setLogService(String logService) {
        this.logService = logService;
    }

    public String getServiceAtt() {
        return serviceAtt;
    }

    public void setServiceAtt(String serviceAtt) {
        this.serviceAtt = serviceAtt;
    }

    public String getGeneService() {
        return geneService;
    }

    public void setGeneService(String geneService) {
        this.geneService = geneService;
    }
}
