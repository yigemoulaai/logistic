layui.use(['element', 'form', 'laydate', 'layer', 'table'], function(){
    let element = layui.element,
    form = layui.form,
    laydate = layui.laydate,
    layer = layui.layer,
    table = layui.table;


    element.on('tab(demo)', function(data){
        if (data.index === 0) {
            table.render({
                elem: '#markerTable',
                height: 'full-170',
                url: nginx_url + '/marker/selectAllMarker', //数据接口
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
                    { title: 'ID', fixed: 'left', sort: true, type: 'numbers' },
                    { field: 'comment', title: '仓库名', sort: true },
                    { field: 'site', title: '地点' },
                    { field: 'longitude', title: '经度', sort: true },
                    { field: 'latitude', title: '纬度' },
                    { fixed: 'right', title:"操作", align:"center", toolbar: '#barDemo' }
                ]]
            });

            // 监听工具条
            table.on('tool(markerTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                let data = obj.data; //获得当前行数据
                let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                // let tr = obj.tr; //获得当前行 tr 的DOM对象

                if(layEvent === 'del'){ //删除
                    layer.confirm('真的删除么', function(index){
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        //向服务端发送删除指令
                        $.ajax({
                            type: "DELETE",
                            url: nginx_url + "/deleteEmp/" + data.employeeCode,
                            success: function (result) {
                                console.log(result);
                            }
                        });
                        layer.msg('删除成功', {
                            time: 800
                        });
                        table.reload('empTable', {
                            url: nginx_url + '/selectAllEmp'
                        })
                    });
                } else if(layEvent === 'edit'){ //编辑
                    layer.open({
                        type: 2,
                        title: '仓库信息修改',
                        content: ['employeeModify.html?employeeCode=' + data.employeeCode, 'no'],
                        area: ['75%', '75%'],
                        shadeClose: true,
                        move: false,
                        end: function() {
                            table.reload('empTable', {
                                url: nginx_url + '/selectAllEmp'
                            })
                        }
                    });
                }
            });
        }
    });
    // 地图仓库添加
    form.on('submit(addMarker)', function (data) {
        $.ajax({
            type: "post",
            url: nginx_url + "/marker/addMarker",
            data: $("#markerForm").serialize(),
            dataType: "json",
            success: function (result) {
                if (result.STATUS === "SUCCESS") {
                    layer.msg('仓库添加成功', {
                        time: 800
                    });
                    $("#resetForm").click();
                } else {
                    layer.msg('仓库添加失败', {
                        time: 800
                    });
                }
            }
        });
        return false;
    });


});

function createTime(v){
    let dateTime;
    let date = new Date();
    date.setTime(v);
    let y = date.getFullYear();
    let m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    let d = date.getDate();
    d = d < 10 ? "0" + d : d;
    dateTime = y + "-" + m + "-" + d;
    return dateTime;
}





