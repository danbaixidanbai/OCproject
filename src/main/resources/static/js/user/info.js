$(function () {
    var getuser='/user/getuserbyid';
    var userId='';
    var headImage='';
    getUser();

    function getUser() {
        $.ajax({
            url:getuser,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode==2){
                    alert(data.errMsg);
                    setTimeout(function(){
                        window.location.href="/login";
                    },3000);
                }else if(data.errCode==3){
                    alert(data.errMsg);
                }else if(data.errCode==1){
                    var user=data.user;
                    var img=user.userImage;
                    headImage=cutString(img);
                    if(img==null || img==''||img=='null')
                    img='/image/header.jpg';
                   }
                   userId=user.userId;
                   $('#user_header').attr("src", img);
                   $('#loginname').html(user.userLoginName);
                   $('#realname').val(user.userName);
                   $("#gender").val(user.userGender);
                   $('#birthday').val(user.userBirthday);
                   $('#mobile').val(user.userMobile);
                   $('#sign').val(user.userSign);
            }
        });
    }

    $('#doupload').click(function (){
        $('#pictureImg').click();
    });

    $('#pictureImg').change(function () {
        var img = $('#pictureImg').val();
        if(oc.photoValid(img)){
            oc.previewUploadImg('pictureImg','user_header');
            $('#user_header').show();
            $('#imgErrSpan').html('');
            return;
        }else{
            $('#imgErrSpan').html('&nbsp;请选择png,jpeg,jpg格式图片');
            $('#pictureImg').val('');
        }
    });


    $('#submit').click(function () {
        var user={};
        user.userId=userId;
        user.userName=$('#realname').val();
        user.userGender=$('#gender').val();
        user.userBirthday=$('#birthday').val();
        user.userMobile=$('#mobile').val();
        user.userSign=$('#sign').val();

        var userImage=$('#pictureImg')[0].files[0];

        var formData=new FormData();
        formData.append("userStr",JSON.stringify(user));
        formData.append("userImage",userImage);
        formData.append("headImage",headImage);

        $.ajax({
            url:'/user/updateuser',
            type:'post',
            dataType:'json',
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                var resp =data;
                if (resp.errCode == 1) {
                    //还可以将img清空
                    $("#myAlert").show().fadeOut(2500);//显示模态框
                    window.location.reload();
                } else {
                    alert(data.errMsg);
                    $("#myAlert").show().fadeOut(2500);//显示模态框
                }
            }
        });
    });
});