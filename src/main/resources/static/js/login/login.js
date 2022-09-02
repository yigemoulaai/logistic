layui.use(['layer', 'form', 'jquery'], function() {
    let $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
    
    form.on('submit(login)', function () {
        let index = layer.load();
        $.ajax({
            type: 'get',
            url: nginx_url + '/login',
            data: {
                'phone': $('#phone').val(),
                'vertifyCode': $('#vertifyCode').val()
            },
            dataType: 'json',
            async: false,
            success: function (result) {
                console.log(result);
                if (result.STATUS === 'SUCCESS') {
                    $.cookie("loginId", result.USER.loginId);
                    let type = result.USER.loginId.slice(0, 2);
                    //$.cookie("type", type);
                    setTimeout(function() {
                        layer.close(index);
                        layer.msg('登录成功', {
                            time: 800,
                            icon: 1
                        }, function () {
                            if(type==='SJ')
                            {
                                window.location.href = "html/appControl/driver/driverManage.html";
                            }
                            else if(type==='KH')
                            {
                                window.location.href = "html/appControl/customer/customerManage.html";
                            }
                            else if(type==='GL')
                            {
                                window.location.href = "html/appControl/manager/manageMain.html";
                            }

                        });
                    }, 800);
                }
                else
                {
                    layer.msg('登录名或密码错误',{
                        time: 800,
                        icon: 1
                    });
                    window.location.href="../login.html";
                }
            }
        });
        return false;
    });
});
