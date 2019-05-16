$(function(){
    //页面数据量
    var pageSize=10;
    //当前页
    var pageIndex=1;
    //总页数
    var pageCount=0;
    //是否上架
    var del='';
    //课程名字
    var courseName='';
    getCmsCourse();
    function getCmsCourse(){
        // 生成新条目的HTML
        var url = '/cms/course/getcmscourse' + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&del=' + del + '&courseName=' + courseName;
        $.getJSON(url, function(data) {
            if (data.errCode==1) {
                var course=data.list;
                //总页数pageCount
                pageCount=data.pageCount;
                var html='';
                for(var i=0;i<course.length;i++){
                    var image=course[i].courseImage;
                    var sale='';
                    if(oc.isEmpty(image)){
                        image='/image/course.png';
                    }else{
                        image=appendString(image);
                    }
                    if(course[i].del==1){
                        sale="课程下架";
                    }if(course[i].del==2){
                        sale="课程上架";
                    }
                    html+='<tr id="'+course[i].courseId+'"><td style="width:600px;"><p>'
                        +'<a href="/cms/course/read?courseId='+course[i].courseId+'">'
                        +'<img src="'+image+'" style="width: 180px;height:100px;float: left;">'
                        +'</a>'
                        +'<div class="ml-15 w-350" style="float:left;">'
                        +'<a href="/cms/course/read?courseId='+course[i].courseId+'">'
                        +'<p class="ellipsis"><strong>'+course[i].courseName+'</strong></p>'
                        +'</a>'
                        +'<p class="ellipsis-multi h-40">简介：'+course[i].courseContent+'</p>'
                        +'</div></p></td>'
                        +'<td>'
                        +'<p>'+course[i].courseClassifyParent.classifyName+'/'+course[i].courseClassify.classifyName+'</p>'
                        +'<p>'+course[i].courseCount+'人在学</p>'
                        +'<p>'+new Date(course[i].courseUpdateTime).Format("yyyy-MM-dd hh:mm")+'</p>'
                        +'</td>'
                        +'<td style="width:120px;">'
                        +'<p>时长：'+course[i].courseTime+'</p>'
                        +'<p><a href="javascript:void(0)" del="'+course[i].del+'" class="sale">'+sale+'</a></p>'
                        +'<p><a href="javascript:void(0)">删除</a></p>'
                        +'</td>'
                        +'</tr>';
                }
                $('#course').html(html);
                // 调用分页函数.参数:当前所在页, 总页数(用总条数 除以 每页显示多少条,在向上取整), ajax函数
                setPage(pageIndex, pageCount, getCmsCourse);
            }else{
                alert(data.errCode+data.errMsg);
            }
        });
    }

    $('#course').on("click",".sale",function () {
        var status=$(this).attr("del");
        var courseId=$(this).parent().parent().parent().attr("id");
        console.log(courseId);
        var flag=1;
        if(status==1){
            flag=2;
        }if(status==2){
            flag=1;
        }
        $.ajax({
            url:'/cms/course/updatedel',
            type:'post',
            dataType:'json',
            data:{"courseId":courseId,"del":flag},
            success:function(resp){
                var errcode = resp.errCode;
                if(errcode == 1){
                    getCmsCourse();
                }else{
                    alert(resp.errCode+resp.errMsg);
                }
            }
        });
    });
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
    }
});