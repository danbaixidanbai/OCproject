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
        var url = '/cms/course/getcourse' + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&del=' + del + '&courseName=' + courseName;
        $.getJSON(url, function(data) {
            if (data.errCode==1) {
                var course=data.course;
                //总页数pageCount
                pageCount=data.pageCount;
                var html='';
                for(var i=0;i<course.length;i++){
                    var image=course[i].courseImage;
                    var sale='课程上架';
                    if(oc.isEmpty(image)){
                        image='/image/course.png';
                    }else{
                        image=appendString(image);
                    }
                    if(course[i].del==1){
                        sale="课程下架";
                    }
                    html+='<tr id="tr-'+course[i].courseId+'"><td style="width:600px;"><p>'
                        +'<a href="/course/read?courseId='+course[i].courseId+'">'
                        +'<img src="'+image+'" style="width: 180px;height:100px;float: left;">'
                        +'</a>'
                        +'<div class="ml-15 w-350" style="float:left;">'
                        +'<a href="/course/read?courseId='+course[i].courseId+'">'
                        +'<p class="ellipsis"><strong>'+course[i].courseName+'</strong></p>'
                        +'</a>'
                        +'</div></p></td>'
                        +'<td>'
                        +'<p>'+course[i].courseClassify.classifyName+'/'+course[i].courseClassifyParent.classifyName+'</p>'
                        +'<p>'+course[i].courseCount+'人在学</p>'
                        +'<p>'+course[i].courseUpdateTime+'</p>'
                        +'</td>'
                        +'<td style="width:120px;">'
                        +'<p>'+course[i].courseTime+'</p>'
                        +'<p><a href="javascript:void(0)" del="'+course[i].del+'">'+sale+'</a></p>'
                        +'<p><a href="javascript:void(0)">删除</a></p>'
                        +'</td>'
                        +'</tr>';
                }
                $('#course').html(html);
                console.log(pageCount);
                // 调用分页函数.参数:当前所在页, 总页数(用总条数 除以 每页显示多少条,在向上取整), ajax函数
                setPage(pageIndex, pageCount, getCmsCourse);
            }else{
                alert(data.errCode+data.errMsg);
            }
        });
    }
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
});