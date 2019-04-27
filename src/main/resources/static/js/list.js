$(function(){
    var classifyId=getQueryString('classifyId');
    var parentId='';
    var pageSize=4;
    var pageIndex=1;
    //总记录数
    var count=0;
    //总页数
    var pageCount=0;
    var url='';
    getClassify();
    function getClassify() {

        //判断classifyId是否为空
        if(!oc.isEmpty(classifyId)){
            getIdexClassify();
        }else{
            url='/courseclassify/getallclassify';
            getALLClassify(url);
        }//加载课程
        getCourse();
    }
   //获取一级和二级分类
    function getALLClassify(url) {
        $.ajax({
            url:url,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode == 1) {
                    var classifyParent=data.classifyParent;
                    var html='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classifyParent.length;i++){
                        html+='<li class="course-nav-item " >'
                            + '<a id="'+classifyParent[i].classifyId+'">'+classifyParent[i].classifyName+'</a>'
                            + '</li>';
                    }
                    var classify=data.classify;
                    var html2='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classify.length;i++){
                        html2+='<li class="course-nav-item ">'
                            + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                            + '</li>';
                    }
                    $('#first').html(html);
                    $('#second').html(html2);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    //获取二级分类
    function getSecondClassify(parentId) {
        $.ajax({
            url:'/courseclassify/getsecondclassify?parentId='+parentId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode == 1) {
                    var classify=data.classify;
                    var html2='<li class="course-nav-item cur-course-nav">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classify.length;i++){
                        html2+='<li class="course-nav-item ">'
                            + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                            + '</li>';
                    }
                    $('#second').html(html2);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }
    //获取首页classify
    function getIdexClassify(){
        $.ajax({
            url:'/courseclassify/getbyclassifyid?classifyId='+classifyId,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success:function (data) {
                if (data.errCode == 1) {
                    var classifyParent=data.classifyParent;
                    parentId=data.parentId;
                    classifyId=data.classifyId;
                    console.log(classifyParent);
                    var html='<li class="course-nav-item ">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classifyParent.length;i++){
                        if(parentId==classifyParent[i].classifyId){
                            html+='<li class="course-nav-item cur-course-nav" >'
                                + '<a id="'+classifyParent[i].classifyId+'">'+classifyParent[i].classifyName+'</a>'
                                + '</li>';
                        }else{
                            html+='<li class="course-nav-item " >'
                                + '<a id="'+classifyParent[i].classifyId+'">'+classifyParent[i].classifyName+'</a>'
                                + '</li>';
                        }

                    }
                    var classify=data.classify;
                    var html2='<li class="course-nav-item">'
                        + '<a >全部</a>'
                        + '</li>';
                    for(var i=0;i<classify.length;i++){
                        if(classifyId==classify[i].classifyId){
                            html2+='<li class="course-nav-item cur-course-nav">'
                                + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                                + '</li>';
                        }else{
                            html2+='<li class="course-nav-item ">'
                                + '<a id="'+classify[i].classifyId+'">'+classify[i].classifyName+'</a>'
                                + '</li>';
                        }

                    }
                    $('#first').html(html);
                    $('#second').html(html2);
                }else{
                    alert(data.errCode+data.errMsg);
                }
            }
        });
    }

    //获取课程
    function getCourse(){
        // 生成新条目的HTML
        var url = '/course/getcourse' + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&classifyId=' + classifyId;
        $.getJSON(url, function(data) {
            if (data.errCode==1) {
                var course=data.course;
                count=data.count;
                //总页数pageCount
                pageCount=data.pageCount;
                var html='';
                for(var i=0;i<course.length;i++){
                    html+='<a href="/course?courseId='+course[i].courseId+'"><div class="course-card-container" style="background-image:url('+appendString(course[i].courseImage)+');">'
                        + '<div class="course-card-top green-bg" >'
                        + '<span>'+course[i].courseName+'</span>'
                        + '</div>'
                        + '<div class="course-card-content">'
                        + '<h3 class="course-card-name">'+course[i].courseContent+'</h3>'
                        + '<div class="course-card-bottom">'
                        + '<div class="course-card-info">热度：'+course[i].courseCount+'</div>'
                        + '</div>'
                        + '</div>'
                        + '</div></a>';
                }
                $('#course').html(html);
                console.log(pageCount);
                    // 调用分页函数.参数:当前所在页, 总页数(用总条数 除以 每页显示多少条,在向上取整), ajax函数
                    setPage(pageIndex, pageCount, getCourse);


            }else{
                alert(data.errCode+data.errMsg);
            }
        });
    }


    $("#first").on("click",".course-nav-item",function (e) {
        parentId=$(e.target).attr('id');
        if(oc.isEmpty(parentId)){
            parentId='';
            url='/courseclassify/getallclassify';
            getALLClassify(url);
            getCourse();
            return;
        }
        $(e.target).parent().addClass('cur-course-nav').siblings()
            .removeClass('cur-course-nav');
        pageIndex=1;
        //加载二级目录
        getSecondClassify(parentId);
        // 加载课程
        getCourse();
    });

    $("#second").on("click",".course-nav-item",function (e) {
        classifyId=$(e.target).attr('id');
        if(oc.isEmpty(classifyId)){
            classifyId='';
        }
        $(e.target).parent().addClass('cur-course-nav').siblings()
            .removeClass('cur-course-nav');
        pageIndex=1;
        //加载课程
        getCourse();
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
});