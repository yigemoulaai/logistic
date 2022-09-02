package com.cqupt.logistic.service;


import com.cqupt.logistic.bean.Comment;
import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.PostOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGoodsService {
    public List<PostOrder> selectGoodsBysite();

    int updateGoodStatus(String driveId, String goodsId, String status);

    int changeGoodsStatus(String id, String status);

    boolean addGoodsOrder(PostOrder postOrder);

    boolean updateGoodsOrder(PostOrder postOrder);


    Page<PostOrder> selectAllCusByPage(Pageable pageable);

    Page<PostOrder> selectCusOrderById(Pageable pageable, String id);


    PostOrder selectOrderByGoodsId(String goodsCode);

    List<PostOrder> selectOrderByDriverId(String driverId);

    Page<PostOrder> selectCompleteOrderById(Pageable pageable, String loginId);

    void saveComment(Comment comment);

    void changeGoodsStatusByGoodsId(String goodsId, String status);

    void saveDriverGet(DriverGet driverGet);
}
