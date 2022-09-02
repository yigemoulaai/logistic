package com.cqupt.logistic.dao;

import com.cqupt.logistic.bean.PostOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface IGoodsDao extends JpaRepository<PostOrder,Long> {
    //根据地点条件查询可接的订单
    @Query("from postOrder where  status like '未%' ")
    List<PostOrder> queryGoodsBySite();

    @Transactional
    @Modifying
    @Query(value = "update post_Order set status =?3 ,driver_id=?1 where id =?2 ", nativeQuery = true)
    int updateGoodStatus(String driveId, String Id, String status);

    @Query(" from postOrder where driver_id=?1 and status like '已%'")
    List<PostOrder> queryOrderById(String id);
    @Transactional
    @Modifying
    @Query(value = "update post_Order set status =?2  where id =?1 ", nativeQuery = true)
    int changeGoodStatus(String id, String status);
    @Transactional
    @Modifying
    @Query(value = "update post_Order set status =?2  where goods_id =?1 ", nativeQuery = true)
    int changeGoodStatusByGoodsStatus(String goodsId, String status);

    @Query(" from postOrder where goodsId=?1")
    PostOrder queryCusOrderByCode(int goodsCode);

    PostOrder findByGoodsId(String goodsCode);

    List<PostOrder> findByDriverId(String driverId);
    @Transactional
    @Modifying
    @Query(value = "update post_Order set status =?2 , goods_arrive_time=?3  where id =?1 ", nativeQuery = true)
    int setGoodsRecived(String driveId, String id, Date date);
}
