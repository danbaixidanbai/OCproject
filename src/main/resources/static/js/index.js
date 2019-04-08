$(function(){
    var index = 0;
    var timer = 4000;
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
        index = index%3;
        rollBg(index);
    }, timer);

    $(".category").popover({
        trigger:'manual',
        placement : 'right',
        html: 'true',
        content : '',
        animation: false
    }).on("mouseenter", function () {
        var cid = $(this).attr('c-id');
        $('#' + cid).show();
        $('#' + cid).hover(function(){
            $('#' + cid).show();
        },function(){
            $('#' + cid).hide();
        });
    }).on("mouseleave", function () {
        var cid = $(this).attr('c-id');
        $('#' + cid).hide();
    });

});
