function sendToServer(){
    if($('.password').val() == ''){
        alert('변경할 비밀번호를 입력해주세요');
        $('.password').focus();
        return false;
    }
    if($('.password_ok').val() == ''){
        alert('비밀번호 확인란을 입력해주세요');
        $('.password_ok').focus();
        return false;
    }
    if($('.password').val() != $('.password_ok').val()){
        alert('비밀번호가 일치하지 않습니다.');
        $('.password_ok').val('');
        $('.password_ok').focus();
        return false;
    }

    //정규식
    const exppw1 = /^(?=.*[a-zA-Z])(?=.*[0-9]).{10,16}$/; //영문, 숫자
    const exppw2 = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{10,16}$/; //영문, 특수문자
    const exppw3 = /^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{10,16}$/; //특수문자, 숫자
    const user_pw = $('.password').val();

    if(!(exppw1.test(user_pw) || exppw2.test(user_pw) || exppw3.test(user_pw))){
        alert("비밀번호 입력조건을 다시한번 확인해 주세요.")
        $('.password').val('');
        $('.password_ok').val('');
        $('.password').focus();
        return false
    }
}