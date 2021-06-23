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

function sendit(){
    if($('input[name=user_id]').val() == ''){
        alert('아이디를 입력해주세요');
        $('input[name=user_id]').focus();
        return false;
    }
    if($('input[name=user_password]').val() == ''){
        alert('비밀번호를 입력해주세요');
        $('input[name=user_password]').focus();
        return false;
    }
}