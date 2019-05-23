$(function(){
    var sessionId=getQueryString("sessionId");
    getSession();

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
});