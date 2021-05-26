$(function(){
    /*클릭 시 nav 나왔다 들어갔다*/
    $('.slidebtn').click(function(){
        if($(this).hasClass('nav_open')){
            $(this).parents('nav').css('right', '-200px');
        }else{
            $(this).parents('nav').css('right', '0');
        }
        $(this).toggleClass('nav_open');
        $(this).children('i').toggleClass('fa-times');
    })

    /*클릭 시 스크롤 이동*/
    $('.scrollup').click(function(){
        $('html, body').animate({scrollTop : 0}, 500);
    })
    $('.scrolldown').click(function(){
        $('html, body').animate({scrollTop : $(document).height()}, 500);
    })
})