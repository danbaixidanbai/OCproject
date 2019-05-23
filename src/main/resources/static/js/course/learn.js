$(function() {
    var courseId = getQueryString("courseId");
    getUser();

    function getUser() {
        $.ajax({
            url:'/user/getuserbyid',
            type: 'get',
            dataType: 'json',
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.errCode == 2) {
                    alert(data.errMsg);
                    window.location.href = "/login";
                } else if (data.errCode == 3) {
                    alert(data.errMsg);
                } else if (data.errCode == 1) {
                    getCourseById();
                    getSessionById();
                    var user = data.user;
                    var userId = user.userId;
                }
            }
        });
    }

    function getCourseById() {
        $.ajax({
            url: '/course/getcoursebyid?courseId=' + courseId,
            datatype: 'json',
            type: 'get',
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.errCode == 1) {
                    var course = data.course;
                    $('#courseName').val(course.courseName);
                    $('#courseCount').val(course.courseCount);
                    $('#courseTime').val(course.courseTime);
                    $('.course-brief').val('简介：'+course.courseContent);
                    var html='<div class="course-title">'+course.courseName+'</div>'
                        +'<a href="/course/video?courseId='+courseId+'" class="learn-btn" >开始学习</a>'
                        + '<div class="static-item">'
                        + '<div class="meta">上次学到</div>'
                        + '<div class="meta-value" title="上次学到">上次学到</div>'
                        + '</div>'
                        + '<div class="static-item">'
                        + '<div class="meta">学习人数</div>'
                        + '<div class="meta-value">'+course.courseCount+'</div>'
                        + '</div>'
                        + '<div class="static-item" style="border:none;">'
                        + '<div class="meta">课程时长</div>'
                        + '<div class="meta-value">'+course.courseTime+'</div>'
                        + '</div>'
                        + '<a id="collectionSpan" cid="'+courseId+'" href="javascript:void(0)" class="following" style="float: right;margin-top:5px;" >'
                        + '</a>'
                        +'<div class="course-brief">简介：'+course.courseContent+'</div>';
                    $('#courseinfo').html(html);
                    getFollow(course.user.userId);
                } else {
                    alert(data.errCode + data.errMsg);
                }
            }
        });
    }
    function getSessionById(){
        $.ajax({
            url:'/coursesession/getcoursesession?courseId='+courseId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.errCode == 1) {
                    var list=data.list;
                    var html='';
                    for(var i=0;i<list.length;i++){
                        html+='<div class="chapter" id="'+list[i].courseSession.courseSessionId+'" >'
                            +'<a href="javascript:void(0);" class="js-open">'
                            + '<h3>'
                            + '<strong><div class="icon-chapter">=</div>'+list[i].courseSession.courseSessionName+'</strong>'
                            + '<span class="drop-down">▼</span>'
                            + '</h3></a><ul class="chapter-sub">';
                        var session=list[i].list;
                        for(var j=0;j<session.length;j++){
                            html+='<a href="/course/video?sessionId='+session[j].courseSessionId+'" >'
                                +'<li id="'+session[j].courseSessionId+'" class="chapter-sub-li">'
                                + '<i class="icon-video">▶</i>'+session[j].courseSessionName+''
                                + '</li></a>'
                        }
                        html+='</ul></div>';
                    }
                    $('#courseSession').html(html);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
//获取课程用户
    function getFollow(userId) {
        $.ajax({
            url:'/user/getuser?userId='+userId,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode==2){
                    alert(data.errMsg);
                }else if(data.errCode==3){
                    alert(data.errMsg);
                }else if(data.errCode==1){
                    var user=data.user;
                    var img=user.userImage;
                    if(img==null || img==''||img=='null')
                        img='/image/header.jpg';
                }
                console.log(img);
                $('#user_header').attr("src", img);
                $('#loginname').text(user.userLoginName);
                $('#sign').text(user.userSign);
            }
        });
    }

    //获取学习进度
    function getLasted(){
        $.ajax({
            url:'/coursesession/getlasted?courseId='+courseId,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if (data.errCode == 1) {

                }
            }
        });
    }

    //实现 章节鼠标焦点 动态效果
    $('#courseSession').on('hover','.chapter li',function(){
        $(this).find('.icon-video').css('color','#FFF');
    },function(){
        $(this).find('.icon-video').css('color','#777');
    });
    $('#courseSession').on('click','.js-open',function () {
        var display = $(this).parent().find('ul').css('display');
        if(display == 'none'){
            $(this).parent().find('ul').css('display','block');
            $(this).find('.drop-down').html('▼');
        }else{
            $(this).parent().find('ul').css('display','none');
            $(this).find('.drop-down').html('▲');
        }
    });



});