package com.cqupt.logistic.service.impl;


import com.cqupt.logistic.bean.Comment;
import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.PostOrder;
import com.cqupt.logistic.dao.ICommentDao;
import com.cqupt.logistic.dao.IDriverGetDao;
import com.cqupt.logistic.dao.IGoodsDao;
import com.cqupt.logistic.dao.IMarkerDao;
import com.cqupt.logistic.service.IGoodsService;
import com.cqupt.logistic.util.CalculateDisUtil;
import com.cqupt.logistic.util.Compute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

@Transactional
@Service(value = "goodsService")
public  class GoodsService implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;
    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private IDriverGetDao driverGetDao;
    @Autowired
    private IMarkerDao markerDao;


    @Override
    public List<PostOrder> selectGoodsBysite() {

        return goodsDao.queryGoodsBySite();
    }



    @Override
    public int updateGoodStatus(String driveId, String Id, String status) {



        return goodsDao.updateGoodStatus(driveId,Id,status);
    }


    @Override
    public int changeGoodsStatus(String id, String status) {
        int flag=0;
        if(status.equals("确认收货"))
        {
            Date date=new Date(System.currentTimeMillis());
            flag=goodsDao.setGoodsRecived(id, status,date);
        }
        else {
            flag=goodsDao.changeGoodStatus(id,status);
        }
        return flag;
    }

    @Override
    public boolean addGoodsOrder(PostOrder postOrder) {
        String des=markerDao.findByComment(postOrder.getGoodsOrderSite()).getSite();
        String origin=markerDao.findByComment(postOrder.getGoodsStartSite()).getSite();
        String distance = CalculateDisUtil.distance(origin, des);
        distance = distance.substring(0, distance.length() - 2);
        double distance1 = Double.valueOf(distance);
        double size=postOrder.getGoodsSize();
        if(distance1>50&&distance1<200)
        {
            distance1=distance1*0.15+size*20;
        }
        else if(distance1>=200&&distance1<500)
        {
            distance1=distance1*0.15+(size*30);
        }
        else if(distance1<800)
        {
            distance1=distance1*0.18+(size*35);
        }
        else {
            distance1=distance1*0.2+(size*40);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        postOrder.setBills(decimalFormat.format(distance1));
        postOrder.setStatus("未接单");
        String goodId = String.valueOf(postOrder.getGoodsStartTime());
        String[] x=goodId.split("-");
        goodId="";
        for(String t:x)
        {
            goodId+=t;
        }
        goodId = "GD" + goodId+String.valueOf((int)((Math.random()*9+1)*100));
        postOrder.setGoodsId(goodId);
        try {
            goodsDao.save(postOrder);
            System.out.println("货运回执单添加成功");
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("货运回执单删除失败");
            return false;
        }
    }

    @Override
    public boolean updateGoodsOrder(PostOrder postOrder) {
        System.out.println(postOrder.getId());
        postOrder.setId(postOrder.getId());
        postOrder.setBills(Compute.computeFee(postOrder));
         goodsDao.save(postOrder);
         return true;
    }



    @Override
    public Page<PostOrder> selectAllCusByPage(Pageable pageable) {
        return goodsDao.findAll(pageable);
    }

    @Override
    public Page<PostOrder> selectCusOrderById(Pageable pageable, String id) {
        PostOrder postOrder = new PostOrder();
        synchronized (this)
        {  postOrder.setCustomerId(id);
            postOrder.setId(null);
            postOrder.setGoodsSize(null);
        }


        Example<PostOrder> example = Example.of(postOrder);
        return goodsDao.findAll(example,pageable);
    }

    @Override
    public PostOrder selectOrderByGoodsId(String goodsCode) {
        return goodsDao.findByGoodsId(goodsCode);
    }

    @Override
    public List<PostOrder> selectOrderByDriverId(String driverId) {

        return goodsDao.findByDriverId(driverId);
    }

    @Override
    public Page<PostOrder> selectCompleteOrderById(Pageable pageable, String loginId) {
        PostOrder postOrder = new PostOrder();
        synchronized (this){
            postOrder.setCustomerId(loginId);
            postOrder.setStatus("确认收货");
        }Example<PostOrder> example = Example.of(postOrder);



        return  goodsDao.findAll(example,pageable);
    }

    @Override
    public void saveComment(Comment comment) {
            commentDao.save(comment);
    }

    @Override
    public void changeGoodsStatusByGoodsId(String goodsId, String status) {
        goodsDao.changeGoodStatusByGoodsStatus(goodsId,status);
    }

    @Override
    public void saveDriverGet(DriverGet driverGet) {
            driverGetDao.save(driverGet);
    }
}





