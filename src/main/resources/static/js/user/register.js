$(function(){
    $('#submit').click(function () {
        var userLoginName = $('#userLoginName').val();
        var userPassword = $('#userPassword').val();
        //验证用户名
        if(!oc.enNumValid(userLoginName)){
            $('#errorMsg').show();
            $('#errorMsg').html("用户名只能为英文或数字");
            return;
        }

        //验证密码
        if(oc.isEmpty(userPassword) || userPassword.length < 6){
            $('#errorMsg').show();
            $('#errorMsg').html("密码至少6位");
            return;
        }

        //验证码不能为空
        var code = $('#verifyCodeActual').val();
        if(oc.isEmpty(code)){
            $('#errorMsg').show();
            $('#errorMsg').html("请输入验证码");
            return;
        }

        var user={};
        user.userLoginName=userLoginName;
        user.userPassword=userPassword;
        var formData=new FormData();
        formData.append("userStr",JSON.stringify(user));
        formData.append('verifyCodeActual',code);

        //提交注册
        $.ajax({
            url:'/user/getregister',
            datatype : 'json',
            type:'post',
            data:formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                var resp = data;
                if (resp.errCode == 0) {
                    $('#errorMsg').show();
                    $('#errorMsg').html("注册成功！3s后跳转到登陆页！");
                    $('#captcha_img').click();
                    setTimeout(function(){
                        window.location.href="/login";
                    },3000)
                } else if(resp.errCode == 1) {
                    $('#errorMsg').show();
                    $('#errorMsg').html("用户名已被注册，请更换！");
                    $('#captcha_img').click();
                }else if(resp.errCode == 2){
                    $('#errorMsg').show();
                    $('#errorMsg').html("验证码输入错误！");
                    $('#captcha_img').click();
                }
            },
            error : function(xhr) {

            }
        });
    });
});


