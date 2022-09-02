
layui.use(['element', 'form', 'laydate', 'layer', 'table','jquery','util'], function(){
    let element = layui.element,
    form = layui.form,
    laydate = layui.laydate,
    layer = layui.layer,
        $ = layui.jquery,
    table = layui.table;
    let loginId=$.cookie('loginId');
/*第一个页面的Js*/
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


    $.ajax({
        type: "get",
        url: nginx_url + '/driverInfo/getMarkerComment',
        async: false,
        success: function (result) {
            $("#customerId").val($.cookie('loginId'));
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
    /*form.on('submit(computeFee)', function () {
        $.ajax({
            type:"post",
            url: nginx_url + "/goodsInfo/computeFee",
            data: $("#cusOrdForm").serialize(),
            dataType: "json",
            async: false,
            success: function (result) {
                console.log(result);
                if (result) {

                    layer.msg(result, {
                        time: 800,
                        icon: 1
                    });
                } else {
                    layer.msg('计算出错', {
                        time: 800,
                        icon: 2
                    });
                }
            }


        });

    });*/

    form.on('submit(addCargoSend)', function () {
        $.ajax({
            type: "post",
            url: nginx_url + "/goodsInfo/addOrder",
            data: $("#cusOrdForm").serialize(),
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.STATUS ==="SUCCESS") {
                    layer.msg('货运单添加成功', {
                        time: 800,
                        icon: 1
                    });
                    $("#resetForm1").click();
                } else {
                    layer.msg('货运单添加失败', {
                        time: 800,
                        icon: 2
                    });
                }
            }

        });return false;
    });


    /*第二个页面的Js*/
    form.verify({
        fax: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^([0-9]{3,4}-)?[0-9]{7,8}$").test(value)){
                return '传真号码格式不正确';
            }
        },
        postcode: function (value, item) {
            if(!new RegExp("^[0-9]{6}$").test(value)){
                return '邮编格式不正确';
            }
        }
    });


    element.on('tab(demo)', function(data){

        if(data.index===1)
        {
            table.render({
                elem: '#cusOrderTable',
                height: 'full-170',
                url: nginx_url + '/goodsInfo/selectCusOrderById/'+loginId, //数据接口
                limit: 10,
                limits: [ 10 ],
                request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    ,limitName: 'limit' //每页数据量的参数名，默认：limit
                },
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msgz
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                },
                page: true //开启分页
                ,cellMinWidth: 60
                ,cols: [[
                    {title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center'},
                    {field: 'goodsId', title: '货物号', align: 'center'},
                    {field: 'status', title: '运单状态', align: 'center'},
                    {field: 'goodsStartTime', title: '货物出发时间', align: 'center',minWidth:150},
                    {field: 'goodsStartSite', title: '货物出发地', align: 'center'},
                    {field: 'goodsOrderSite', title: '货物目的地', align: 'center'},
                    {field: 'bills', title: '货运金额', align: 'center'},
                    {fixed: 'right', title: "操作", align: "center", toolbar: '#barDemo1', width: 300}
                ]]
            });
            table.on('tool(cusOrderTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                let data = obj.data; //获得当前行数据
                let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                let tr = obj.tr; //获得当前行 tr 的DOM对象

                if(layEvent === 'confrimGet'){ //确认收货
                    if(data['status']==="确认收货")
                    {
                        layer.msg('您已确认过了，请去做评价', {
                            time: 800,
                            icon: 1
                        });
                    }
                    else {
                        layer.confirm('已经收到货物了吗？', function(){
                            let status=3;//
                            $.ajax({
                                type: "put",
                                id:'driverOrderTable',
                                url: nginx_url + "/goodsInfo/changeGoodStatus/" + data.id+"/"+status,
                                async: false,
                                dataType: 'json',
                                success: function (result) {
                                    console.log(result);
                                    if (result.STATUS === "SUCCESS") {
                                        layer.msg('收货成功', {
                                            time: 800,
                                            icon: 1
                                        });
                                    } else {
                                        layer.msg('系统出现故障，请稍后再试', {
                                            time: 800,
                                            icon: 5
                                        });
                                    }
                                }
                            });
                            table.reload('driverOrderTable', {
                                url: nginx_url + '/goodsInfo/selectCusOrderById/'+loginId, //数据接口
                            });
                        });
                    }

                } else if(layEvent === 'edit'){ //编辑
                    layer.open({
                        type: 2,
                        title: '客户订单信息修改',
                        content: [ 'cusOrderDetail.html?orderId=' + data['goodsId'], 'no' ],
                        area: [ '85%', '85%' ],
                        shadeClose: true,
                        move: false,
                        end: function() {
                            table.reload('cusOrderTable', {
                                url: nginx_url + '/goodsInfo/selectCusOrderById/'+loginId, //数据接口
                            })
                        }
                    });
                } else if (layEvent === 'detail') {
                    layer.open({
                    type: 2,
                    title: '客户订单信息查看',
                    content: ['cusOrderDetail.html?orderId=' + data['goodsId'], 'no'],
                    area: ['85%', '85%'],
                    shadeClose: true,
                    move: false,
                    end: function () {
                        table.reload('cusOrderTable', {
                            url: nginx_url + '/goodsInfo/selectCusOrderById/' + loginId, //数据接口
                        })
                    }
                });
                }
            });
        }
        if(data.index===2)
        {
            $.ajax({
                type: "get",
                url: nginx_url + "/selectCusOrderByCode/" + loginId,
                success: function (result) {
                    console.log(result.data);
                    $("#customerCode").val(result.data.customerCode);
                    $("#customer").val(result.data.customer);
                    $("#phone").val(result.data.phone);
                    $("#fax").val(result.data.fax);
                    $("#address").val(result.data.address);
                    $("#postCode").val(result.data.postCode);
                    $("#linkman").val(result.data.linkman);
                    $("#linkmanMobile").val(result.data.linkmanMobile);
                    $("#customerType").val(result.data.customerType);
                    $("#enterpriseProperty").val(result.data.enterpriseProperty);
                    $("#enterpriseSize").val(result.data.enterpriseSize);
                    $("#email").val(result.data.email);
                }
            });
        }
        if (data.index === 3) {
            table.render({
                elem: '#cusCommentTable',
                height: 'full-170',
                url: nginx_url + '/goodsInfo/selectCompleteOrderById/'+loginId, //数据接口
                limit: 10,
                limits: [ 10 ],
                request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    ,limitName: 'limit' //每页数据量的参数名，默认：limit
                },
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msgz
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                },
                page: true //开启分页
                ,cellMinWidth: 60
                ,cols: [[
                    {title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center'},
                    {field: 'goodsId', title: '货物号', align: 'center'},
                    {field: 'goodsStartTime', title: '货物出发时间', align: 'center',minWidth:150},
                    {field: 'goodsStartSite', title: '货物出发地', align: 'center'},
                    {field: 'goodsOrderSite', title: '货物目的地', align: 'center'},
                    {field: 'bills', title: '货运金额', align: 'center'},
                    {fixed: 'right', title: "操作", align: "center", toolbar: '#barDemo', width: 300}
                ]]
            });

            // 监听工具条
            table.on('tool(cusCommentTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                let data = obj.data; //获得当前行数据
                let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                let tr = obj.tr; //获得当前行 tr 的DOM对象

                if(layEvent === 'del'){ //删除
                    layer.confirm('真的删除么', function(index){
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        //向服务端发送删除指令
                        $.ajax({
                            type: "DELETE",
                            url: nginx_url + "/deleteCus/" + data.customerCode,
                            success: function (result) {
                                console.log(result);
                            }
                        });
                        layer.msg('删除成功', {
                            time: 800
                        })
                    });
                }
                else if(layEvent === 'edit'){ //编辑
                    layer.open({
                        type: 2,
                        title: '客户信息修改',
                        content: [ 'customerModify.html?customerCode=' + data.customerCode, 'no' ],
                        area: [ '85%', '85%' ],
                        shadeClose: true,
                        move: false,
                        end: function() {
                            table.reload('cusTable', {
                                url: nginx_url + '/selectAllCus'
                            })
                        }
                    });
                } else if (layEvent === 'detail') {
                    layer.open({
                        type: 2,
                        title: '客户评价',
                        content: [ 'comment.html?customerCode=' + data['goodsId'], 'no' ],
                        area: [ '85%', '85%' ],
                        shadeClose: true,
                        move: false,
                    });
                }
            });
        }
    });

    form.on('submit(updateCus)', function (data) {
        $.ajax({
            type: "post",
            url: nginx_url + "/updateCus",
            data: $("#cusForm").serialize(),
            dataType: "json",
            success: function (result) {
                console.log(result);
                if (result.STATUS === "SUCCESS") {
                    layer.msg('客户信息修改成功', {
                        time: 800
                    });
                    $("#resetForm").click();
                } else {
                    layer.msg('客户修改失败', {
                        time: 800
                    });
                }
                console.log(result);
            }
        });
    });

});
