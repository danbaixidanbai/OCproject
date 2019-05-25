$(function(){
 /*   //页面数据量
    var pageSize=10;
    //当前页
    var pageIndex=1;
    //总页数
    var pageCount=0;
    //是否上架
    var del='';
    //课程名字
    var courseName='';*/
    getCmsCourse();
    function getCmsCourse(){
        // 生成新条目的HTML
        var url = '/collection/getcourse';
        $.getJSON(url, function(data) {
            if (data.errCode==1) {
                var collection=data.list;
                console.log(collection);
                var html='';
                for(var i=0;i<collection.length;i++){
                    var course=collection[i].course;
                    var image=course.courseImage;
                    if(oc.isEmpty(image)){
                        image='/image/course.png';
                    }else{
                        image=appendString(image);
                    }
                    html+='<tr id="'+course.courseId+'"><td style="width:600px;"><p>'
                        +'<a href="/course/learn?courseId='+course.courseId+'">'
                        +'<img src="'+image+'" style="width: 180px;height:100px;float: left;">'
                        +'</a>'
                        +'<div class="ml-15 w-350" style="float:left;">'
                        +'<a href="/course/learn?courseId='+course.courseId+'">'
                        +'<p class="ellipsis"><strong>'+course.courseName+'</strong></p>'
                        +'</a>'
                        +'<p class="ellipsis-multi h-40">简介：'+course.courseContent+'</p>'
                        +'</div></p></td>'
                        +'<td>'
                        /*+'<p>'+course[i].courseClassifyParent.classifyName+'/'+course[i].courseClassify.classifyName+'</p>'*/
                        +'<p>'+course.courseCount+'人在学</p>'
                        +'<p>'+new Date(course.courseUpdateTime).Format("yyyy-MM-dd hh:mm")+'</p>'
                        +'</td>'
                        +'<td style="width:120px;">'
                        +'<p>时长：'+course.courseTime+'</p>'
                        +'<p><a href="javascript:void(0)" class="cfollow">取消关注</a></p>'
                        +'</td>'
                        +'</tr>';
                }
                $('#course').html(html);
            }else if(data.errCode==3){
                alert(data.errCode+data.errMsg);
                window.location.href="/login";
            }else if(data.errCode==2){
                $('#course').html(data.errMsg);
            }else{
                alert(data.errCode+data.errMsg);
            }
        });
    }
    $('#course').on('click','.cfollow',function () {
        var courseId=$(this).parent().parent().parent().attr('id');
        console.log(courseId);
        $.ajax({
            url:'/collection/docollection?courseId='+courseId+'&flag=0',
            type:'get',
            dataType:'json',
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if(data.errCode == 6) {
                    window.location.reload();
                }else if(data.errCode == 3){
                    alert(data.errCode+data.errMsg);
                    window.location.href="/login";
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    });
/*
    $("#submit").click(function () {
        courseName=$("#name").val();
        pageIndex=1;
        getCmsCourse();
    });

    $("#select").on('change', function() {
        del=$("#select").val();
        pageIndex=1;
        getCmsCourse();
    });

    function setPage(pageCurrent, pageSum, callback) {
        $(".pagination").bootstrapPaginator({
            //设置版本号
            bootstrapMajorVersion: 3,
            // 显示第几页
            currentPage: pageCurrent,
            // 总页数
            totalPages: pageSum,
            //当单击操作按钮的时候, 执行该函数, 调用ajax渲染页面
            onPageClicked: function (event,originalEvent,type,page) {
                // 把当前点击的页码赋值给currentPage, 调用ajax,渲染页面
                pageIndex = page;
                //console.log(currentPage);
                callback && callback();
            }
        })
    }
    //课程添加获取一级分类
    $('#addcourse').click(function () {
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
        course.courseName=$("#courseName")[0].value;
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
        $.ajax({
            url:'/course/addcourse',
            datatype :'json',
            data:formData,
            type:'post',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if(data.errCode==1){
                    window.location.reload();
                }else if(data.errCode==4){
                    window.location.href='/login';
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
    //课程删除
    function doDelete(id){
        Modal.confirm('课程章节将一并删除，确定删除?',function(){
            $.ajax({
                url:'${s.base}/course/doDelete.html',
                type:'POST',
                dataType:'json',
                data:{"id":id},
                success:function(resp){
                    var errcode = resp.errcode;
                    if(errcode == 0){
                        $('#tr-'+id).remove();
                    }
                }
            });
        });
    }*/
});