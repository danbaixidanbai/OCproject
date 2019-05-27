$(function(){
    $('#submit').click(function () {
        var userLoginName = $('#userLoginName').val();
        var userPassword = $('#userPassword').val();
        //验证用户名
        if (!oc.enNumValid(userLoginName)) {
            $('#errorMsg').show();
            $('#errorMsg').html("用户名只能为英文或数字");
            return;
        }
        //验证码不能为空
        var code = $('#verifyCodeActual').val();
        if(oc.isEmpty(code)){
            $('#errorMsg').show();
            $('#errorMsg').html("请输入验证码");
            return;
        }

        var user = {};
        user.userLoginName = userLoginName;
        user.userPassword = userPassword;
        var formData = new FormData();
        formData.append("userStr", JSON.stringify(user));
        formData.append('verifyCodeActual', code);

        //提交登录
        $.ajax({
            url: '/user/getlogin',
            datatype: 'json',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                var resp = data;
                if (resp.errCode == 0) {
                    window.location.href = "/index";
                } else if (resp.errCode == 1) {
                    $('#errorMsg').show();
                    $('#errorMsg').html("用户名或密码错误！");
                    $('#captcha_img').click();
                } else if (resp.errCode == 2) {
                    $('#errorMsg').show();
                    $('#errorMsg').html("验证码输入错误！");
                    $('#captcha_img').click();
                }else if(resp.errCode == 3){
                    $('#errorMsg').show();
                    $('#errorMsg').html("该账户已被禁用！");
                    $('#captcha_img').click();
                }
            }
        });
    });
});