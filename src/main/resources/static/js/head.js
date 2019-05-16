$(function(){
    var userId='';
    isLogin();

    function isLogin() {
        $.ajax({
            url:'/user/islogin',
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
                        img='http://cdn.poipoipo.top/'+user.userImage;
                    }
                    html += '<a href="/home" class="header-nav-item"  style="margin-left:0px;width:40px;height:40px;" id="userdetail">'
                        + '<img id="headerUser" alt="" src="' + img + '"style="width: 35px;height:35px;border-radius:100%;">'
                        + '</a>';
                    $('#headerUserHeader').html(html);
                    //$('#headerUserHeader') .css("margin-top","-100px");
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
                }/*else if(resp.errCode == 1) {
                    alert("你还没登陆，请重新登录");
                }*/


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