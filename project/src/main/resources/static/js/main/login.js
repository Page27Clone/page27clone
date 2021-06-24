function sendit(){
    if($('input[name=loginId]').val() == ''){
        alert('아이디를 입력해주세요');
        $('input[name=loginId]').focus();
        return false;
    }
    if($('input[name=password]').val() == ''){
        alert('비밀번호를 입력해주세요');
        $('input[name=password]').focus();
        return false;
    }

}
$(function(){
    if($('#loginTrue').val() == 'loginTrue'){
        location.href = '/main/index';
    }

})