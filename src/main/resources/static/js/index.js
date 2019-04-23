$(function(){
    console.log(index);
    var index = 0;
    var timer = 4000;
    var size=3;
    getMainBg();
    console.log(1);
    getMenu();


    function getMainBg(){
        $.ajax({
            url:'/index/getmainbg',
            datatype :'json',
            type:'post',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.errCode == 1) {
                    var news = data.news;
                    size = data.size;
                    var html = '';
                    var html2='';
                    news.map(function (item,value) {
                        html += '<a href="' + item.newsUrl + '">'
                            + '<div class="main-bg-item " style="background-image:url('+appendString(item.newsImage)+');">'
                            + '</div></a>';
                        html2+='<a></a>';

                    });
                    $('#mainbg').html(html);
                    $('#bgnav').html(html2);

                }else{
                    alert(data.errMsg);
                }
            }
        });
    }
    function getMenu(){
        $.ajax({
            url:'/index/getmenu',
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.errCode == 1) {
                    var list = data.list;
                    var html = '';
                    var html2 = '';
                    list.map(function (item,value) {
                            var courseClassifyId=item.courseClassify.classifyId;
                            html+='<div class="category" c-id="'+courseClassifyId+'">'
                                + '<a><div class="group">'+item.courseClassify.classifyName+'</div></a>'
                                + '</div>';
                        listSeond=item.list;
                        //二级分类
                        html2+= '<div class="main-category-submenu-content" id="'+courseClassifyId+'">';
                        listSeond.map(function (demo,value) {
                            var courseUrl='/list?classifyId='+demo.classifyId;
                            html2 +='<a href="'+courseUrl+'">'+demo.classifyName+'</a><span></span>';
                        });
                        html2+='</div>';
                    });
                    console.log(html);
                    console.log(html2);
                    $('.main-category-menu').html(html);
                    $('.main-category').append(html2);
                }else{
                    alert(data.errMsg);
                }
            }
        });
    }

    /*function appendString(key){
        var img='http://pp9sub7xv.bkt.clouddn.com/'+key;
        //alert(img);
        return img;
    }*/

    $('.bg-nav a').click(function(){
        index = $('.bg-nav a').index($(this));
        rollBg(index);
    });
    $('.index-roll-item').click(function(){
        index = $('.index-roll-item').index($(this));
        rollBg(index);
    });
    var rollBg = function(i){
        $('.main-bg-item').fadeOut(1000);
        $($('.main-bg-item')[i]).fadeIn(1000);
        $('.bg-nav a').removeClass('cur');
        $($('.bg-nav a')[i]).addClass('cur');
        $('.index-roll-item').removeClass('cur');
        $($('.index-roll-item')[i]).addClass('cur');
    }
    setInterval(function(){
        index += 1;
        index = index%size;
        rollBg(index);
    }, timer);

    $(".main-category-menu").on("mouseenter", ".category",function () {
        var cid = $(this).attr('c-id');
        $('#' + cid).show();
        $('#' + cid).hover(function(){
            $('#' + cid).show();
        },function(){
            $('#' + cid).hide();
        });
    }).on("mouseleave",".category", function () {
        var cid = $(this).attr('c-id');
        $('#' + cid).hide();
    });

});
