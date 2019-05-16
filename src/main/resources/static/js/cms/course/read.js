$(function(){
    var courseId=getQueryString("courseId");
    getCourseById();
    getSessionById();
    var classify='';
    var classifypatrnt='';
    var sessionParent='';
    //用于删除图片服务器图片的key
    var imageKey='';
    function getCourseById() {
        $.ajax({
            url:'/course/getcoursebyid?courseId='+courseId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if(data.errCode==1){
                    var course=data.course;
                    var image=course.courseImage;
                    if(oc.isEmpty(image)){
                        image='/image/course.png';
                    }else{
                        imageKey=image;
                        image=appendString(image);
                    }
                    classify=course.courseClassify.classifyId;
                    classifypatrnt=course.courseClassifyParent.classifyId;
                    $("#imgs").attr("src",image);
                    $('#courseName').text(course.courseName);
                    $('#courseContent').text(course.courseContent);
                    var courseClassify=course.courseClassifyParent.classifyName+'/'+course.courseClassify.classifyName;
                    $('#courseClassify').text(courseClassify);
                    $('#courseCount').text(course.courseCount+'人在学');
                    $('#courseUser').text(course.user.userName);
                    $('#courseUpdateTime').text(new Date(course.courseUpdateTime).Format("yyyy-MM-dd hh:mm"));
                    $('#courseTime').text(course.courseTime);
                    //修改弹窗
                    $("#coursePicture").attr("src",image);
                    $('#name').val(course.courseName);
                    $('#time').val(course.courseTime);
                    $('#brief').val(course.courseContent);
                    /*$("#classify").html('<option value="'+classifypatrnt+'">'+course.courseClassifyParent.classifyName+'</option>');*/
                    $("#subClassify").html('<option value="'+classify+'">'+course.courseClassify.classifyName+'</option>');
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });

    }
    //获取章节
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
                            + '<h3>'
                            + '<strong id="sectionTitle_'+list[i].courseSession.courseSessionId+'" >'+list[i].courseSession.courseSessionName+'</strong>'
                            + '</h3><ul class="chapter-sub">';
                        var session=list[i].list;
                        for(var j=0;j<session.length;j++){
                            var status='';
                            if(session[j].courseSessionStatus==1){
                                status='通过';
                            }else if(session[j].courseSessionStatus==2){
                                status='不通过';
                            }else{
                                status='审核中';
                            }
                            html+='<li id="'+session[j].courseSessionId+'" class="chapter-sub-li">'
                                + '<span id="sectionSubTitle_'+session[j].courseSessionId+'" >'+session[j].courseSessionName+'</span>'
                                + '<a href="javascript:void(0);" class="chapter-edit  m-video" data-src="'+appendString(session[j].courseSessionVideoUrl)+'">预览</a>'
                                + '<div class="chapter-edit status" status="'+session[j].courseSessionStatus+'"><a href="javascript:void(0);" class="chapter-edit">'+status+'</a></div>'
                                + '</li>'
                        }
                        html+='</ul></div>';
                    }
                    console.log(html);
                    $('#session').html(html);
                }else if(data.errCode == 3){
                    $('#session').html=("你还没有添加章节");
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }

    $('#session').on('click','.status',function () {
        var html='<select id="select" class="chapter-edit">'
                +'<option value="1">通过</option>'
                +'<option value="2">不通过</option>'
                +'<option value="3">审核中</option>'
                +'</select>>';
        $(this).html(html);
        var status=$(this).attr('status');
        $("#select").val(status);
    });

    $('#session').on('change','#select',function () {
        var status=$("#select").val();
        var sessionId=$(this).parent().parent().attr('id');
        console.log(status);
        $.ajax({
            url:'/coursesession/updatesessionstatus?status='+status+'&sessionId='+sessionId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if(data.errCode==1){
                    alert("审核修改成功！");
                    window.location.reload();
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });

});