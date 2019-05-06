$(function(){
    var courseId=getQueryString("courseId");
    getCourseById();
    getSessionById();
    var classify='';
    var classifypatrnt='';
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
                        html+='<div class="chapter" id="chapter-'+list[i].courseSession.courseSessionId+'" >'
                            + '<h3>'
                            + '<strong id="sectionTitle_'+list[i].courseSession.courseSessionId+'" >'+list[i].courseSession.courseSessionName+'</strong>'
                            + '<a href="javascript:void(0);" class="chapter-edit" style="margin-right:20px;">删除</a>'
                            + '<a href="javascript:void(0);" class="chapter-edit" >修改</a>'
                            + '</h3><ul class="chapter-sub">';
                        var session=list[i].list;
                        for(var j=0;j<session.length;j++){
                            html+='<li id="chapter-sub-li-'+session[j].courseSessionId+'" class="chapter-sub-li">'
                                + '<span id="sectionSubTitle_'+session[j].courseSessionId+'" >'+session[j].courseSessionName+'</span>'
                                + '<a href="javascript:void(0);" class="chapter-edit  m-video" data-src="'+appendString(session[j].courseSessionVideoUrl)+'">预览</a>'
                                + '<a href="javascript:void(0);" class="chapter-edit" style="margin-right:20px;">删除</a>'
                                + '<a href="javascript:void(0);" class="chapter-edit" >修改</a>'
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

    /*$('#session').on('click',"li",function () {
        var courseSessionId=$(this).attr()
    });*/



    $('#edit').click(function () {
        getCourseById();
        $.ajax({
            url:'/courseclassify/getparent',
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if(data.errCode==1){
                    var html='';
                    var list=data.list;
                    for(var i=0;i<list.length;i++){
                        html+='<option value="'+list[i].classifyId+'">'+list[i].classifyName+'</option>';
                    }
                    $("#classify").html(html);
                    $("#classify").val(classifypatrnt);
                    $("#myModal").modal('show');
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });

    //二级联动
    $("#classify").on('change',function () {
        var parentId=$("#classify").val();
        $.ajax({
            url:'/courseclassify/getsecondclassify?parentId='+parentId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if(data.errCode==1){
                    var html='';
                    var list=data.classify;
                    for(var i=0;i<list.length;i++){
                        html+='<option value="'+list[i].classifyId+'">'+list[i].classifyName+'</option>';
                    }
                    $("#subClassify").html(html);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });

    $('#save').click(function () {
        var course={};
        course.courseId=courseId;
        course.courseName=$('#courseName').text();
        course.courseClassify={
            classifyId: $("#subClassify").val()
        },
        course.courseClassifyParent={
            classifyId: $("#classify").val()
        },
        course.courseContent=$('#brief').val();
        var formData=new FormData();
        formData.append("courseStr",JSON.stringify(course));
        var picture=$('#pictureImg')[0].files[0];
        formData.append("picture",picture);
        formData.append("imggeKey",imageKey);
        $.ajax({
            url:'/course/updatecourse',
            datatype :'json',
            data:formData,
            type:'post',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if(data.errCode==1){
                    window.location.reload();
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });



    $('#doupload').click(function (){
        $('#pictureImg').click();
    });

    $('#pictureImg').change(function () {
        var img = $('#pictureImg').val();
        if(oc.photoValid(img)){
            oc.previewUploadImg('pictureImg','coursePicture');
            $('#coursePicture').show();
            $('#imgErrSpan').html('');
            return;
        }else{
            $('#imgErrSpan').html('&nbsp;请选择png,jpeg,jpg格式图片');
            $('#pictureImg').val('');
        }
    });
});