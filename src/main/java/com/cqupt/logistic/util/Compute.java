package com.cqupt.logistic.util;


import com.cqupt.logistic.bean.PostOrder;

public class Compute {
    public  static  String computeFee(PostOrder postOrder)
    {
        String ordersite=postOrder.getGoodsOrderSite();
        String startsite=postOrder.getGoodsStartSite();
        String fee = CalculateDisUtil.distance(postOrder.getGoodsOrderSite(), postOrder.getGoodsStartSite());
        fee = fee.substring(0, fee.length() - 2);
        fee=String.valueOf(Float.valueOf(fee)*1.2);
        return  fee;

    }
}
