$(function() {
    var courseId = getQueryString("courseId");
    getUser();
    iscollection();
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
                    getLasted();
                   /* var user = data.user;
                    var userId = user.userId;*/
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
                    $('#courseName').html(course.courseName);
                    $('#courseCount').html(course.courseCount);
                    $('#courseTime').html(course.courseTime);
                    $('.course-brief').html('简介：'+course.courseContent);
                    getFollow(course.user.userId);
                    getComment(course.user.userId);
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
                $("#u-id").val(userId);
                $('#user_header').attr("src", img);
                $('#loginname').text(user.userLoginName);
                $('#sign').text(user.userSign);
                isfollow(userId);
            }
        });
    }

    function getComment(userId) {
        $.ajax({
            url:'/coursecomment/getcomment?courseId='+courseId,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if (data.errCode == 1) {
                    var list=data.courseCommentDto;
                    console.log(list);
                    var html='';
                    for(var i=0;i<list.length;i++){
                        var courseComment=list[i].courseComment;
                        console.log(courseComment);
                        var image=courseComment.user.userImage;
                        if(oc.isEmpty(image)){
                            image='/image/header.jpg';
                        }else{
                            image=appendString(image);
                        }
                        html+='<div class="comment clearfix">'
                            + '<div class="comment-header"><img class="lecturer-uimg" src="'+image+'"></div>'
                            + '<div class="comment-main">'
                            + '<div class="user-name">'+courseComment.user.userLoginName+'</div>'
                            + '<div class="comment-content">'+courseComment.courseCommentContent+'</div>'
                            + '<div class="comment-footer">'
                            + '<span>时间：'+new Date(courseComment.updateTime).Format("yyyy-MM-dd hh:mm")+'</span>'
                            +'<a herf="javaScript:void(0);" class="commentreply" u-id="'+courseComment.user.userId+'" comment-id="'+courseComment.courseCommentId+'">回复</a>'
                            + '</div>';
                            var reply=list[i].list;
                            for(var j=0;j<reply.length;j++){
                                var img=reply[j].user.userImage;
                                if(oc.isEmpty(img)){
                                    img='/image/header.jpg';
                                }else{
                                    img=appendString(img);
                                }
                                var html2='';
                                if(reply[j].toReplyId==0||reply[j].toUser == undefined || reply[j].toUser == null || reply[j].toUser == '' || reply[j].toUser == 'null' || reply[j].toUser == 'undefined'){
                                    html2+=reply[j].user.userLoginName+':';
                                }else{
                                    html2+=reply[j].user.userLoginName+' 回复<a herf="javaScript:void(0);">@'+reply[j].toUser.userLoginName+'</a>';
                                }
                               html+='<div class="comment-reply clearfix" reply-id="'+reply[j].replyId+'">' +
                                   '<div class="comment-header">' +
                                   '<img class="lecturer-uimg" src="'+img+'">' +
                                   '</div>' +
                                   '<div class="user-name"style="float:left;">'+html2+'<span class="reply-content">'
                                   + reply[j].replyContent
                                   + '</span>'
                                   + '<div class="comment-footer">'
                                   + '<span>时间：'+new Date(reply[j].updateTime).Format("yyyy-MM-dd hh:mm")+'</span>'
                                   +'<a herf="javaScript:void(0);" class="commentreply" u-id="'+reply[j].user.userId+'" t-id="'+reply[j].toUser.userId+'" comment-id="'+courseComment.courseCommentId+'">回复</a>'
                                   + '</div>'
                                   + '</div>'
                                   + '</div>';
                            }
                        html+='</div>'
                            + '</div>'
                    }
                    console.log(html);
                    $('#comment').append(html);
                }
            }
        });
    }
    //评论回复弹窗
    $('#comment').on("click",'.commentreply',function () {
        $(".reply_textarea").remove();
        $(this).parent().append('<div style="margin-top: 20px;" class="reply_textarea">' +
                                '<div class="commentForm" style="margin: 5px 0px;">' +
                                '<textarea class="replycontent" rows="" cols="100"></textarea>' +
                                '<input type="button" value="发表评论" class="btn replysubmit">' +
                                '</div>' +
                                '</div>');
    });
    //评论回复提交
    $('#comment').on("click",'.replysubmit',function () {
        var uid=$(this).parent().parent().prev().attr('u-id');
        var tid=$(this).parent().parent().prev().attr('t-id');
        var replyId=0;
        if(tid == undefined || tid== null || tid=='null'|| tid=='undefined'){
            replyId=0;
        }else{
            replyId=$(this).parent().parent().parent().parent().parent().attr('reply-id');
        }
        var commentId=$(this).parent().parent().prev().attr('comment-id');
        var replyContent=$('.replycontent').val();
        console.log(uid);
        console.log(commentId);
        var formData=new FormData();
        formData.append("toUserId",uid);
        formData.append("commentId",commentId);
        formData.append("replyContent",replyContent);
        formData.append("replyId",replyId);
        $.ajax({
            url:'/coursecomment/addreply',
            type:'post',
            dataType:'json',
            contentType: "application/json",
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if (data.errCode == 1) {
                    alert("回复成功！！！");
                    window.location.reload();
                }else if(data.errCode==3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });

    });

    //发布评论
    $('#submit').click(function () {
        var formData=new FormData();
        var comment=$('#content').val();
        formData.append("courseId",courseId);
        formData.append("comment",comment);
        $.ajax({
            url:'/coursecomment/addcomment',
            type:'post',
            dataType:'json',
            contentType: "application/json",
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if (data.errCode == 1) {
                    alert("评论成功！！！");
                    window.location.reload();
                }else if(data.errCode==3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });

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
                    var courseSession=data.courseSession;
                    var html='/course/video?sessionId='+courseSession.courseSessionId;
                    $('#studytext').html("你还没开始学习");
                    $("#study").attr('href',html);
                }else if(data.errCode == 5) {
                    var courseSession=data.courseSession;
                    var html='/course/video?sessionId='+courseSession.courseSessionId;
                    $('#studytext').html(courseSession.courseSessionName);
                    $("#study").attr('href',html);
                    $("#study").text("继续学习");
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }

    //判断是否收藏
    function iscollection(){
        $.ajax({
            url:'/collection/iscollection?courseId='+courseId,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode == 1) {
                    $('#collectionSpan').removeClass('following');
                    $('#collectionSpan').attr('class','followed');
                }else if(data.errCode == 4){
                    $('#collectionSpan').removeClass('followed');
                    $('#collectionSpan').attr('class','following');
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    //点击更改是否收藏
    $('#courseinfo').on('click','#collectionSpan',function () {
        var status=$('#collectionSpan').attr('class');
        console.log(status);
        var flag=0;
        if(status=='following'){
            flag=1;
        }
        $.ajax({
            url:'/collection/docollection?courseId='+courseId+'&flag='+flag,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode == 1) {
                    alert("收藏成功");
                    $('#collectionSpan').attr('class','followed');
                }else if(data.errCode == 6){
                    alert("取消收藏成功");
                    $('#collectionSpan').attr('class','following');
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });
    //判断是否关注
    function isfollow(userId){
        $.ajax({
            url:'/userfollow/isfollow?userId='+userId,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode == 1) {
                    $('#followSpan').html('已关注');
                    $('#flag').val("yes");
                }else if(data.errCode == 4){
                    $('#flag').val("no");
                    $('#followSpan').html('+关注');
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    $('#followSpan').click(function () {
        var followId=$('#u-id').val();
        console.log(followId);
        var status=$('#flag').val();
        var flag=0;
        if(status=='no'){
            flag=1;
        }
        $.ajax({
            url:'/userfollow/dofollow?followId='+followId+'&flag='+flag,
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode == 1) {
                    alert("关注成功");
                    $('#flag').val("yes");
                    $('#followSpan').html('已关注');
                }else if(data.errCode == 6){
                    alert("取消关注成功");
                    $('#flag').val("no");
                    $('#followSpan').html('+关注');
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });


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