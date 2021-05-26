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