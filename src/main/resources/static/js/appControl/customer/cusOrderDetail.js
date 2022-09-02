layui.use(['element', 'form', 'laydate', 'jquery', 'layer', 'table'], function() {
    let element = layui.element,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer,
        table = layui.table,
        $ = layui.jquery;
    lay('.test-item').each(function () {
        laydate.render({
            elem: this,
            trigger: 'click'
        })
    });
    laydate.render({
        elem: '#goodsStartTime',
        value: new Date()
    });
    let goodsCode = window.location.href.split("=")[1];
    $.ajax({
        type: "get",
        url: nginx_url + '/driverInfo/getMarkerComment',
        async: false,
        success: function (result) {
            $.each(result, function (i, item) {
                let option = "<option value='" + item + "'>";
                option += item;
                option += "</option>";
                $("#goodsOrderSite").append(option);
                $("#goodsStartSite").append(option);
            });
            form.render('select');
        }
    });
    $.ajax({
        type: "get",
        url: nginx_url + "/goodsInfo/selectOrderByGoodsId/" + goodsCode,
        dataType: 'json',
        async: false,
        success: function (result) {
            console.log(result);
            $("#id").val(result.id);
            $("#status").val(result.status);
            $("#customerId").val(result.customerId);
            $("#receiverId").val(result.receiverId);
            $("#receiverPhone").val(result.receiverPhone);
            $("#goodsStartSite").each(function() {
                // this代表的是<option></option>，对option再进行遍历
                $(this).children("option").each(function() {
                    // 判断需要对那个选项进行回显
                    if (this.value == result.goodsStartSite) {
                        console.log($(this).text());
                        // 进行回显
                        $(this).attr("selected","selected");
                    }
                });
            });
            $("#goodsOrderSite").each(function() {
                // this代表的是<option></option>，对option再进行遍历
                $(this).children("option").each(function() {
                    // 判断需要对那个选项进行回显
                    if (this.value == result.goodsOrderSite) {
                        console.log($(this).text());
                        // 进行回显
                        $(this).attr("selected","selected");
                    }
                });
            });
            $("#goodsType").each(function() {
                // this代表的是<option></option>，对option再进行遍历
                $(this).children("option").each(function() {
                    // 判断需要对那个选项进行回显
                    if (this.value == result.goodsType) {
                        console.log($(this).text());
                        // 进行回显
                        $(this).attr("selected","selected");
                    }
                });
            });
            $("#goodsSize").val(result.goodsSize);
            form.render();
        }
    });
    form.on('submit(updateCargoSend)', function () {
        $.ajax({
            type: "post",
            url: nginx_url + "/goodsInfo/updateOrder",
            data: $("#cusOrdDetailForm").serialize(),
            dataType: "json",
            async: false,
            success: function (result) {
                if (result === "SUCCESS") {
                    layer.msg('货运单修改成功', {
                        time: 800,
                        icon: 1
                    });
                    $("#resetForm1").click();
                } else {
                    layer.msg('货运单修改成功', {
                        time: 800,
                        icon: 2
                    });
                }
            }
        });
        return false;
    });

});