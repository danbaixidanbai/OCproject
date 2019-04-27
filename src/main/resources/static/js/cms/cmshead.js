$(function(){
    isLogin();

    function isLogin() {
        $.ajax({
            url:'/cms/islogin',
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                var admin=data.admin;
                if (data.errCode == 0) {
                    $("#admin").text("管理员："+admin.userLoginName);
                }else if(data.errCode == 1) {
                    window.location.href='/cms/cmslogin';
                }


            }
        });
    }

});