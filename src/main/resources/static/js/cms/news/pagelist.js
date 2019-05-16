$(function(){
    var pageIndex=1;
    var pageSize=100;
    var pageCount=0;

   getpagelist();

    function getpagelist() {
        // 生成新条目的HTML
        var url = '/cms/news/getpagelist' + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize;
        $.ajax({
            url:url,
            datatype :'json',
            type:'get',
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.errCode == 0) {
                    var html='';
                    var list=data.list;
                    pageCount=data.pageCount;
                    console.log(pageCount);
                    for(var i=0;i<list.length;i++){
                        html+='<tr id="tr-'+list[i].newsId+'">'
                            + '<td style="width:600px;">'
                            + '<p>'
                            + '<img src="'+appendString(list[i].newsImage)+'" style="width: 250px;height:150px;float: left;">'
                            + '<div class="ml-15" style="float:left;">'
                            + '<p class="ellipsis"  title="'+list[i].newsName+'">'+list[i].newsName+'</p>'
                            + '<p class="ellipsis-multi h-40" >链接：'
                            + '<a href="'+list[i].newsUrl+'" target="_blank">'+list[i].newsUrl+'</a>'
                            + '</p></div>'
                            + '</p>'
                            + '</td>'
                            + '<td style="width:120px;">'
                            + '<p>推荐权重：'+list[i].newsPriority+'</p>'
                            + '<p><a href="/cms/news/newsinfo?newsId='+list[i].newsId+'" >修改</a></p>'
                            + '<p><a href="javascript:void(0);" id="'+list[i].newsId+'" class="delnews">删除</a></p>'
                            + '</td>'
                            + '</tr>';
                    }

                    $("#newstb").html(html);
                }else if(data.errCode == 1) {
                    alert("errMsg"+data.errMsg);
                }
            }
        });
    }
    $("#newstb").on("click",".delnews",function () {
        var newsId=$(this).attr('id');
        console.log(newsId);
           if(confirm('确定删除?')){
               $.ajax({
                   url:'/cms/news/deletenews',
                   type:'POST',
                   dataType:'json',
                   data:{"newsId":newsId},
                   success:function(resp){
                       var errcode = resp.errcode;
                       if(errcode == 0){
                           $('#tr-'+id).remove();//删除成功，直接移除 tr
                           window.location.reload();
                       }
                   }
               });
           }

        });

});