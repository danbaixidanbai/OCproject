$(function(){
    getCmsCourse();
    function getCmsCourse(){
        // 生成新条目的HTML
        var url = '/cms/getalluser';
        $.getJSON(url, function(data) {
            if (data.errCode==1) {
                var user=data.list;
                console.log(user);
                var html='';
                var jy='解除禁用';
                for(var i=0;i<user.length;i++){
                    var image=user[i].userImage;
                    if(oc.isEmpty(image)){
                        image='/image/header.jpg';
                    }else{
                        image=appendString(image);
                    }
                    if(user[i].del==1){
                        jy='禁用';
                    }else if(user[i].del==0){
                        jy='解除禁用';
                    }
                    html+='<tr id="'+user[i].userId+'"><td style="width:600px;"><p>'
                        +'<img src="'+image+'" style="width: 180px;height:100px;float: left;">'
                        +'<div class="ml-15 w-350" style="float:left;">'
                        +'<p class="ellipsis"><strong>'+user[i].userLoginName+'</strong></p>'
                        +'<p class="ellipsis"><strong>姓名：'+user[i].userName+'</strong></p>'
                        +'<p class="ellipsis-multi h-40">个人简介：'+user[i].userSign+'</p>'
                        +'</div></p></td>'
                        +'<td style="width:120px;">'
                        +'<p><a href="javascript:void(0)" class="jy" del-id="'+user[i].del+'">'+jy+'</a></p>'
                        +'</td>'
                        +'</tr>';
                }
                $('#user').html(html);
            }else if(data.errCode==2){
                $('#user').html(data.errMsg);
            }else{
                alert(data.errCode+data.errMsg);
            }
        });
    }
    $('#user').on('click','.jy',function () {
        var del=$(this).attr('del-id');
        var jy='禁用';
        var userId=$(this).parent().parent().parent().attr('id');
        console.log(userId);
        if(del==0){
            del=1;
            jy='禁用';
        }else if(del==1){
            del=0;
            jy='解除禁用';
        }
        var formData=new FormData();
        formData.append("del",del);
        formData.append("userId",userId);
        $.ajax({
            url:'/cms/user/setjy',
            datatype :'json',
            data:formData,
            type:'post',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode==1) {
                    $(this).attr('del-id',del);
                    $(this).text(jy);
                    window.location.reload();
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }

        });
    });
});