$(function(){
    var url='user/islogin';
    isLogin();

    function isLogin() {
        $.ajax({
            url:url,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                var resp = data;
                var user=data.user;
                var img='';
                var html = '';
                var loginMsg='';
                if (resp.errCode == 0) {
                    img=user.userImage;
                    if(img==null || img==''||img=='null'){
                        img='/image/header.jpg';
                    }else{
                        img='http://pp9sub7xv.bkt.clouddn.com/'+user.userImage;
                    }
                    html += '<a href="/home" class="header-nav-item"  style="margin-left:0px;width:40px;height:40px;margin-top=-100;" id="userdetail">'
                        + '<img id="headerUser" alt="" src="' + img + '"style="width: 70px;height:70px;border-radius:50%;">'
                        + '</a>';
                    $('#headerUserHeader').html(html);

                    loginMsg+='<div id="userdetailHtml" style="display: none;">'
                        + '<div style="padding:10px;">'
                        + '<div style="margin-top:10px;">'
                        + '<span style="font-size: 16px;">'+user.userLoginName+'</span>'
                        + '</div>'
                        + '<div style="margin-top:20px;">'
                        + '<a id="curCourseA" class="link-a" target="_blank" href="">'
                        + '<div id="curCourseSpan" class="ellipsis" style="font-size: 16px;width:220px;height:30px;overflow:hidden;font-weight:bold;" title="" ></div>'
                        + '</a></div>'
                        + '<div style="margin-top:5px;">'
                        + '<a id="curCourseSectionA"  class="link-a" target="_blank" href="">'
                        + '<div id="curCourseSectionSpan" class="ellipsis"  style="font-size: 14px;width:220px;height:30px;overflow:hidden;" title=""></div>'
                        + '</a></div>'
                        + '<div style="margin-top:20px;border-top:1px solid #eee;width:200px;padding-top:10px;">'
                        + '<a class="link-a" href="/logout">'
                        + '<span>退出</span>'
                        + '</a></div>'
                        + '</div></div>';
                    $("#userdetail").append(loginMsg);
                    show();
                }else if(resp.errCode == 1) {
                    alert("系统繁忙，请重新登录");
                    return;
                }


            }
        });
    }

    function show() {
        $("#userdetail").popover({
            trigger:'manual',
            placement : 'bottom',
            html: true,
            content : '<div id="userdetailContent" style="width:300px;height:200px;"></div>',
            animation: false
        }).on("mouseenter", function () {
            var _this = this;
            $(this).popover("show");
            var userdetailHtml = $('#userdetailHtml').html();
            $('#userdetailContent').html(userdetailHtml);
            $(this).siblings(".popover").on("mouseleave", function () {
                $(_this).popover('hide');
            });
        }).on("mouseleave", function () {
            var _this = this;
            setTimeout(function () {
                if (!$(".popover:hover").length) {
                    $(_this).popover("hide")
                }
            }, 500);
        });
    }
});