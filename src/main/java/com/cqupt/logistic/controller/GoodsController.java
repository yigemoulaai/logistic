package com.cqupt.logistic.controller;

import com.cqupt.logistic.bean.Comment;
import com.cqupt.logistic.bean.DriverGet;
import com.cqupt.logistic.bean.PostOrder;
import com.cqupt.logistic.service.IGoodsService;
import com.cqupt.logistic.util.Antcore.Ant;
import com.cqupt.logistic.util.Antcore.Plan;
import com.cqupt.logistic.util.Compute;
import com.cqupt.logistic.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping(value = "/goodsInfo")
@RestController
@CrossOrigin
@ControllerAdvice
public class GoodsController extends  ReturnType {

    /*
    * 筛选未运货单
    *
    * */
    @Autowired
   private IGoodsService goodsService;


    /*查询客户的发单的数据
    * */
    @RequestMapping(value = "/selectGoodsBySite",method = RequestMethod.GET)
    public Map<String,Object> selectGoodsBySite()
    {

        List<PostOrder> list=goodsService.selectGoodsBysite();
        if(list==null)
        {
            System.out.println("ok");
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("code",200);
        resultMap.put("msg","");
        resultMap.put("count","1000");
        resultMap.put("data",list);
        return resultMap;
    }
    /*
    * 根据司机ID获取已接单
    * */

    @RequestMapping(value = "/selectOrderByDriverId/{loginId}",method = RequestMethod.GET)
    public Map<String,Object> selectOrderByDriverId(@PathVariable("loginId") String driverId)
    {
        List<PostOrder> list=goodsService.selectOrderByDriverId(driverId);
        if(list==null)
        {
            System.out.println("ok");
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("code",200);
        resultMap.put("msg","");
        resultMap.put("count","1000");
        resultMap.put("data",list);
        return resultMap;
    }
    /*
    * 根据订单Id查出订单详细
    * */

    @RequestMapping(value = "/selectCusOrderById/{loginId}",method = RequestMethod.GET)
    public Result selectCusOrderById(@PathVariable("loginId") String id,@RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(pageNum-1, limit);
        Page<PostOrder> page = goodsService.selectCusOrderById(pageable,id);
        Result result = new Result(200, "SUCCESS", (int) page.getTotalElements(), page.getContent());
        return result;
    }
    /*
    *
    * 用户更新货物数据
    * */
    @RequestMapping(value = "/updateGoodStatus/{Id}/{driverId}",method = RequestMethod.PUT)
    public Map<?,?> updateGoodStatus(@PathVariable("driverId") String driverId, @PathVariable("Id") String Id)
    {
        Map<String,String> map=new HashMap<>();
        int flag;
               flag= goodsService.updateGoodStatus(driverId,Id ,"已接单");
        if (flag==0) {
           map.put("STATUS",ERROR);
        }
        else  map.put("STATUS",SUCCESS);
        return map;
    }
    /*
    * 司机更新订单状态
    * */
    @RequestMapping(value = "/changeGoodStatus/{Id}/{status}",method = RequestMethod.PUT)
    public Map<?, ?> changeGoodsStatus(@PathVariable("Id") String id, @PathVariable("status") String status)
    {
        Map<String,String> map=new HashMap<>();
        int flag=0;
        if(status.equals("2")){flag= goodsService.changeGoodsStatus(id ,"已出发");}
        if(status.equals("3")){
            flag= goodsService.changeGoodsStatus(id ,"确认收货");
        }
        if (flag==0) {
            map.put("STATUS", "ERROR");
        }
        else map.put("STATUS", "SUCCESS");
        return map;

    }
    /*用户添加货运订单*/
    @RequestMapping(value = "/addOrder",method = RequestMethod.POST, produces = "application/json")
    public  Map<?,?> addGoodsOrder(PostOrder postOrder){
        Map<String,String> map=new HashMap<>();
        boolean flag;
        flag= goodsService.addGoodsOrder(postOrder);
        if (flag!= false) {
            map.put("STATUS",SUCCESS);
        }
        else map.put("STATUS",ERROR);
        return map;
    }
/*
* 根据地点之间的距离和货物数计算运费*/
    @RequestMapping(value = "/computeFee",method = RequestMethod.POST,produces = "application/json")
    public  String computeFee(PostOrder postOrder)
    {

        return Compute.computeFee(postOrder);

    }
/*更新用户订单*/
    @RequestMapping(value = "/updateOrder",method = RequestMethod.POST, produces = "application/json")
    public  String updateGoodsOrder(PostOrder postOrder){
        boolean flag;
        flag= goodsService.updateGoodsOrder(postOrder);
        if (flag== false) {
            return ERROR;
        }
        return SUCCESS;
    }

   /*
   * 查询订单详情
   * */
   @RequestMapping(value = "/selectOrderByGoodsId/{goodsCode}",method = RequestMethod.GET)
   public PostOrder selectOrderByCode(@PathVariable("goodsCode") String  goodsCode) {

       PostOrder postOrder = goodsService.selectOrderByGoodsId(goodsCode);
       return postOrder;
   }
   /*
   * 根据用户ID查询已经完成的订单
   * */
   @RequestMapping(value = "/selectCompleteOrderById/{loginId}",method = RequestMethod.GET)
       public Result selectCompleteOrderById(@PathVariable("loginId") String  loginId,@RequestParam("pageNum") int pageNum, @RequestParam("limit") int limit) {

       Pageable pageable = PageRequest.of(pageNum-1, limit);
       Page<PostOrder> page = goodsService.selectCompleteOrderById(pageable,loginId);
       Result result = new Result(200, "SUCCESS", (int) page.getTotalElements(), page.getContent());
       return result;
   }
   /*
   *
   * 根据订单号 提交评价
   * */
   @RequestMapping(value = "/comment/{goodsId}",method = RequestMethod.POST, produces = "application/json")
   public  Map<String, Object> saveComment(@PathVariable("goodsId") String  goodsId, Comment comment) {
       PostOrder postOrder = goodsService.selectOrderByGoodsId(goodsId);
       comment.setCustomerId(postOrder.getCustomerId());
       comment.setDriverId(postOrder.getDriverId());
       comment.setLogService("5");
       comment.setGeneService("5");
       comment.setServiceAtt("5");
       DriverGet driverGet=new DriverGet();
        driverGet.setDriverId(postOrder.getDriverId());
        driverGet.setCargoOrderSite(postOrder.getGoodsOrderSite());
        driverGet.setCargoStartSite(postOrder.getGoodsStartSite());
        driverGet.setGoodsId(postOrder.getGoodsId());
        driverGet.setGoodsState("完成评价");
        driverGet.setCustomerId(postOrder.getCustomerId());
       double fax=Double.parseDouble(postOrder.getBills());
        fax=fax*(Double.parseDouble(comment.getGeneService())/5.0);
       DecimalFormat decimalFormat = new DecimalFormat("#0.00");
       driverGet.setCargoGet(Double.parseDouble(decimalFormat.format(fax*0.98)));
       synchronized (this)
       {
           goodsService.saveComment(comment);
           goodsService.changeGoodsStatusByGoodsId(goodsId,"完成评价");
           goodsService.saveDriverGet(driverGet);
       }
       Map<String,Object> map=new HashMap<>();
       map.put("STATUS",SUCCESS);
       return map;
   }
/*
* 根据出发地 和途径地指定路线规划
*
* */
@RequestMapping(value = "/querySchedule",method = RequestMethod.GET, produces = "application/json")
public  Map<?,?> querySchedule(String beginMarker,String passMarker) throws Exception {
    Map<String,Object> map=new HashMap<>();
    String[] address=passMarker.split("/");
    Ant ant=Plan.plan(beginMarker,address);
    int[] path=ant.m_nPath;
    List<String> list=new ArrayList<>();
    list.add(beginMarker);
    for(int i=1;i<path.length;i++)
    {
        System.out.println(address[path[i]-1]);
        list.add(address[path[i]-1]);
    }
    map.put("STATUS",SUCCESS);
    String canPath="最短路径为";
    for(int i=0;i<list.size();i++)
    {
        canPath+="("+i+" "+list.get(i)+")"+"\n";
    }
    canPath+="距离为"+ant.m_dbPathLength/1000+"KM";
    map.put("data",canPath);
    return map;
}
}
