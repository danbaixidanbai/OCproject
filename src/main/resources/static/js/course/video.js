$(function(){
    var sessionId=getQueryString("sessionId");
    getSession();
    getComment();
    function getSession() {
        $.ajax({
            url:'/coursesession/getvideosession?sessionId='+sessionId,
            type: 'get',
            dataType: 'json',
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.errCode == 1) {
                    var courseSession=data.courseSession;
                    console.log(courseSession.courseSessionName);
                    $('#sessionName').html(courseSession.courseSessionName);
                    var url=appendString(courseSession.courseSessionVideoUrl);
                    $('#sessionVideo').attr("src",url);
                    var courseId=courseSession.course.courseId;
                    getSessionById(courseId);
                }else{
                    alert(data.errCode + data.errMsg);
                }
            }
        });
    }

    function getSessionById(courseId){
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
                        html+='<div class="chapter" style="padding: 0px ;border: none;" id="'+list[i].courseSession.courseSessionId+'" >'
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

    function getComment() {
        $.ajax({
            url:'/coursecomment/getcomment?sessionId='+sessionId,
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
                }else{
                    $('#comment').append(data.errMsg);
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
        formData.append("sessionId",sessionId);
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

});