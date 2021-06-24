function sendit(){
    //alert("sendit호출");
    if($('#user_id').val() == ""){
        alert("아이디는 필수 입력값입니다.");
        $('#user_id').focus();
        return false;
    }

    if($('#user_password').val() == ""){
        alert("비밀번호는 필수 입력값입니다.");
        $('#user_password').focus();
        return false;
    }
    if($('#user_password_ok').val() == ""){
        alert("비밀번호확인은 필수 입력값입니다.");
        $('#user_password_ok').focus();
        return false;
    }
    if($('#user_name').val() == ""){
        alert("이름은 필수 입력값입니다.");
        $('#user_name').focus();
        return false;
    }

    if($('#sample6_postcode').val() == ""){
        alert("주소 항목은 필수 입력값입니다.");
        $('#sample6_postcode').focus();
        return false;
    }

    let isempty = false;
    $('.phone_number').each(function(){
        if($(this).val() == ""){
            alert("휴대전화 항목은 필수 입력값입니다.");
            $(this).focus();
            isempty = true
            return false;
        }
    })
    if(isempty){
        return false;
    }
    
    if($('#email').val() == ""){
        alert("이메일 항목은 필수 입력값입니다.");
        $('#email').focus();
        return false;
    }

    if($('#user_password').val() != $('#user_password_ok').val()){
        alert("비밀번호 확인의 값이 다릅니다.");
        $('#user_password_ok').val('');
        $('#user_password_ok').focus();
        return false;
    }

    //정규식
    const exppw1 = /^(?=.*[a-zA-Z])(?=.*[0-9]).{10,16}$/; //영문, 숫자
    const exppw2 = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{10,16}$/; //영문, 특수문자
    const exppw3 = /^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{10,16}$/; //특수문자, 숫자
    const user_pw = $('#user_password').val();

    if(!(exppw1.test(user_pw) || exppw2.test(user_pw) || exppw3.test(user_pw))){
        alert("비밀번호 입력조건을 다시한번 확인해 주세요.")
        $('#user_password').val('');
        $('#user_password').focus();
        return false
    }

}
$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('.withdrawbtn').on('click', function(){
        if(confirm('탈퇴하면 적립금도 함께 삭제됩니다. 정말로 탈퇴 하시겠습니까?')){
            const user_pw_temp = $('#user_password').val();
            $.ajax({
                type: 'DELETE',
                url: '/main/withdrawal',
                data: {user_pw : user_pw_temp},
                beforeSend : function(xhr)
                {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                    xhr.setRequestHeader(header, token);
                }
            }).done(function(word){
                alert(word); //정상적으로 회원탈퇴 되었습니다. or 비밀번호가 올바르지 않습니다.
                location.reload();
            }).fail(function (error){
                alert(JSON.stringify(error));
            })
        }
    })
})