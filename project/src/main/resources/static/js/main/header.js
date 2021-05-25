$(function(){
    //일정 스크롤 시 헤더 fixed, 맨위 로고 나타남
    let top_menuArea = $('.top_menuArea').offset().top;
    let top_categoryArea = $('.top_categoryArea').offset().top;
    $(window).on('scroll', function(){
        let window = $(this).scrollTop();
        if(top_menuArea < window){
            $('.top_menuArea').addClass('top_menuArea_fixed');
            $('.top_logoArea').css('margin-top', '45px');
        }else{
            $('.top_menuArea').removeClass('top_menuArea_fixed');
            $('.top_logoArea').css('margin-top', '0px');
        }
        if(top_categoryArea < window+45){
            $('.top_categoryArea').addClass('top_categoryArea_fixed');
            $('section').css('margin-top', '50px');
            $('.logo_between img').fadeIn('fast');
        }else{
            $('.top_categoryArea').removeClass('top_categoryArea_fixed');
            $('section').css('margin-top', '0px');
            $('.logo_between img').fadeOut('fast');
        }
    });
    
    // category hover 시 소 카테고리 fadein fadeout
    $('.menu_cell_category_drop').hover(
        function(){
        $(this).children('.menu_cell_category_dropdown').fadeIn('fast');
        $(this).children('.menu_cell_category_dropdown').css('display' ,'flex'); //fadeIn시 dispaly:block으로 동작하므로 flex로 바꿔줄 필요가 있다. 
        },
        function(){
            $(this).children('.menu_cell_category_dropdown').fadeOut('fast');
        }
    )
    //searchbox dropdown
    $('.search_all').click(function(){
        if($(this).children('i').hasClass('fa-search')){
            $(this).children('i').removeClass('fa-search');
            $(this).children('i').addClass('fa-times');
        }else{
            $(this).children('i').removeClass('fa-times');
            $(this).children('i').addClass('fa-search');
        }
        $(this).children('.search_form_dropdown').slideToggle('fast');
    })
    $('.search_all .search_form_dropdown').click(function(e){
        e.stopPropagation();
    })
    $('.search_form_dropdown .fa-search').click(function(){
        $('.search_form_dropdown').submit();
    })

})