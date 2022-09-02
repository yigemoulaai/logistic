function getUrlParam(name){
    //正则表达式过滤
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    //正则规则
    console.log("reg==="+reg);
    //search：返回URL的查询部分
    console.log("location.search==="+location.search);
    //substr（1）：从字符串第一个位置中提取一些字符
    console.log("location.search.substr(1)==="+location.search.substr(1));
    //match（）：在字符串内检索与正则表达式匹配的指定值，返回一个数组给r
    console.log("window.location.search.substr(1).match(reg)==="+window.location.search.substr(1).match(reg));
    var r = window.location.search.substr(1).match(reg);
    //获取r数组中下标为2的值；（下标从0开始），用decodeURI（）进行解码
    console.log("decodeURI(r[2])==="+decodeURI(r[2]));
    console.log("-----------------分隔符------------------------");
    if (r != null) return decodeURI(r[2]); return null;

}

layui.use(['layer', 'form', 'element', 'laydate', 'jquery', 'table','util'], function() {
    let element = layui.element,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        table = layui.table;
    element.on('tab(navigationFilter)', function(data) {
        var start = getUrlParam('start');
        var order = getUrlParam('order');
        if (data.index === 0) {
            table.render({
                elem: '#navigationTable',
                height: 'full-170',
                url: nginx_url + '/route/getNavigation/' + start + '/' + order, //数据接口
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
                    {field: 'action', title: '操作', align: 'center'},
                    {field: 'duration', title: '时间(s)', align: 'center'},
                    {field: 'assistant_action', title: '协助行动', align: 'center'},
                    {field: 'distance', title: '距离(m)', align: 'center'},
                    {field: 'instruction', title: '指导', align: 'center',minWidth:150},
                    {field: 'toll_road', title: '收费公路', align: 'center'},
                    {field: 'road', title: '公路', align: 'center'},
                ]]
            });
        }
    });

});