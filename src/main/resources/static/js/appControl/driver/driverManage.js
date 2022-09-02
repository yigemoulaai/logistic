layui.use(['layer', 'form', 'element', 'laydate', 'jquery', 'table','util'], function() {
    let element = layui.element,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        table = layui.table;
    let  loginId=$.cookie('loginId');
    form.render();
    laydate.render({
        elem: '#birthday',
        type: 'date',
        theme: '#393D49'
    });

    form.on('switch(checkCondition)', function (data) {
        if (data.elem.checked === true) {
            $("#companyCar").val(true);
        } else {
            $("#companyCar").val(false);
        }
    });

    form.on('submit(addDriver)', function () {
        $.ajax({
            type: 'post',
            url: nginx_url + '/driverInfo/add',
            data: $("#driverForm").serialize(),
            dataType: 'json',
            success: function (result) {
                console.log(result);
                if (result.STATUS === 'SUCCESS') {
                    layer.msg('正在审核中', {
                        time: 800,
                        icon: 1
                    });
                    $("#resetForm").click();
                } else {
                    layer.msg('添加失败', {
                        time: 800,
                        icon: 1
                    });
                }
            }
        });
        return false;
    });

    element.on('tab(driverFilter)', function(data){
        /*司机工资表的页面*/
        if(data.index===0)
        {
            table.render({
                    elem: '#drivergetTable',
                    height: 'full-170',
                    url: nginx_url + '/driverInfo/selectGetById/'+loginId, //数据接口
                    limit: 10,
                    limits: [ 10 ],
                    request: {
                        pageName: 'pageNum', //页码的参数名称，默认：page
                        limitName: 'limit' //每页数据量的参数名，默认：limit
                },
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msg
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                },
                //page: true, //开启分页
                cellMinWidth: 80,
                cols: [[
                    { title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center' },
                    { field: 'customerId', title: '用户ID', align: 'center' },
                    { field: 'goodsId', title: '运单号', align: 'center' },
                    { field: 'goodsState', title: '运单状态', align: 'center' },
                    { field: 'cargoStartSite', title: '货物出发地', align: 'center' },
                    { field: 'cargoOrderSite', title: '货物目的地', align: 'center' },
                    { field: 'cargoGet' , title: '货运金额', align: 'center'}
                ]]
            });

        }
        /*司机接单的页面*/
        if(data.index===1) {
            table.render({
                elem: '#cargoselectTable',
                height: 'full-170',
                url: nginx_url + '/goodsInfo/selectGoodsBySite', //数据接口
                limit: 10,
                limits: [10],
                request: {
                    pageName: 'pageNum', //页码的参数名称，默认：page
                    limitName: 'limit' //每页数据量的参数名，默认：limit
                },
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msg
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                },
                //page: true, //开启分页
                cellMinWidth: 80,
                cols: [[
                    {title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center'},
                    {field: 'customerId', title: '客户ID', align: 'center'},
                    {field: 'goodsId', title: '货物号', align: 'center'},
                    {field: 'status', title: '运单状态', align: 'center'},
                    {field: 'goodsStartTime', title: '货物出发时间', align: 'center',minWidth:150},
                    {field: 'goodsStartSite', title: '货物出发地', align: 'center'},
                    {field: 'goodsOrderSite', title: '货物目的地', align: 'center'},
                    {field: 'bills', title: '货运金额', align: 'center'},
                    {fixed: 'right', title: "操作", align: "center", toolbar: '#barDemo1', width: 150}
                ]]
            });
            table.on('tool(cargoSelectTool)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                let data = obj.data; //获得当前行数据
                let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                if (layEvent === 'change') { //修改
                    layer.confirm('确定接单吗', function () {
                        // obj.del(); //修改对应行（tr）的DOM结构，并更新缓存
                        // layer.close(index);
                        //向服务端发送修改指令
                        $.ajax({
                            type: "put",
                            url: nginx_url + "/goodsInfo/updateGoodStatus/" + data.id+"/"+loginId,
                            async: false,
                            dataType: 'json',
                            success: function (result) {
                                console.log(result);
                                if (result.STATUS === "SUCCESS") {
                                    layer.msg('接单成功', {
                                        time: 800,
                                        icon: 1
                                    });
                                } else {
                                    layer.msg('接单失败', {
                                        time: 800,
                                        icon: 5
                                    });
                                }
                            }
                        });
                        table.reload('cargoSelectTable', {
                            url: nginx_url + '/goodsInfo/selectGoodsBySite', //数据接口
                        });
                    });

                }
            })
        }
        if (data.index === 2)
        {
            table.render({
                elem: '#driverOrderTable',
                height: 'full-170',
                url: nginx_url + '/goodsInfo/selectOrderByDriverId/'+loginId, //数据接口
                limit: 10,
                limits: [ 10 ],
                request: {
                    pageName: 'pageNum', //页码的参数名称，默认：page
                    limitName: 'limit' //每页数据量的参数名，默认：limit
                },
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 200, //成功的状态码，默认：0
                    msgName: 'msg', //状态信息的字段名称，默认：msg
                    countName: 'count', //数据总数的字段名称，默认：count
                    dataName: 'data' //数据列表的字段名称，默认：data
                },
                page: true, //开启分页
                cellMinWidth: 80,
                cols: [[
                    {title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center'},
                    {field: 'customerId', title: '客户ID', align: 'center'},
                    {field: 'goodsId', title: '货物号', align: 'center'},
                    {field: 'status', title: '运单状态', align: 'center'},
                    {field: 'goodsStartTime', title: '货物出发时间', align: 'center',minWidth:150},
                    {field: 'goodsStartSite', title: '货物出发地', align: 'center'},
                    {field: 'goodsOrderSite', title: '货物目的地', align: 'center'},
                    {field: 'bills', title: '货运金额', align: 'center'},
                    {fixed: 'right', title: "操作", align: "center", toolbar: '#barDemo', width: 280}
                ]]

            });
           /* table.on('row(driverOrderTable)',function () {

            }*/
            // 监听工具条
            table.on('tool(driverOrderTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                let data = obj.data; //获得当前行数据
                let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

                if(layEvent === 'startDelivery'){ //开始配送
                    if(data['status']==='已接单'){
                        layer.confirm('已经准备好', function(){
                            let status=2;//
                            $.ajax({
                                type: "put",
                                id:'driverOrderTable',
                                url: nginx_url + "/goodsInfo/changeGoodStatus/" + data.id+"/"+status,
                                async: false,
                                dataType: 'json',
                                success: function (result) {
                                    console.log(result);
                                    if (result.STATUS === "SUCCESS") {
                                        layer.msg('开始配送', {
                                            time: 800,
                                            icon: 1
                                        });
                                    } else {
                                        layer.msg('配送等待中', {
                                            time: 800,
                                            icon: 5
                                        });
                                    }
                                }
                            });
                            table.reload('driverOrderTable', {
                                url: nginx_url + '/goodsInfo/selectOrderByDriverId/'+loginId,
                            });
                        });
                    }
                    else {
                        layer.msg('已经在配送中', {
                            time: 800,
                            icon: 1
                        });
                    }


                }
                else if(layEvent==='daoHang')
                {
                    console.log(data['goodsStartSite']);
                   $.cookie('aPlace',data['goodsStartSite']);
                    layer.open({
                    type: 2,
                    title: '路线导航',
                    content: [ 'daoHang.html?start='+data['goodsStartSite']+"&order="+data['goodsOrderSite'], 'no' ],
                    area: [ '85%', '85%' ],
                    shadeClose: true,
                    move: false,
                });
                }
                else if(layEvent==='navigation')
                {
                    console.log(data['goodsStartSite']);
                    $.cookie('aPlace',data['goodsStartSite']);
                    layer.open({
                        type: 2,
                        title: '路线导航',
                        content: [ 'navigation.html?start='+data['goodsStartSite']+"&order="+data['goodsOrderSite'], 'no' ],
                        area: [ '85%', '85%' ],
                        shadeClose: true,
                        move: false,

                    });

                }
            });
        }
    })


});
