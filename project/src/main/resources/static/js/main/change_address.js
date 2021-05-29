function sendit(){
    //alert("sendit호출");
    if($('#address_name').val() == ""){
        alert("배송지명 항목은 필수 입력값입니다.");
        $('#address_name').focus();
        return false;
    }
    if($('#address_recipient').val() == ""){
        alert("성명 항목은 필수 입력값입니다.");
        $('#address_recipient').focus();
        return false;
    }
    if($('#sample6_postcode').val() == ""){
        alert("주소 항목은 필수 입력값입니다.");
        $('#sample6_postcode').focus();
        return false;
    }

    let isempty = false;
    $('.address_home_number').each(function(){
        if($(this).val() == ""){
            alert("일반전화 항목은 필수 입력값입니다.");
            $(this).focus();
            isempty = true
            return false;       //foreach문 탈출
        }
    })
    if(isempty){
        return false;
    }
    $('.address_phone_number').each(function(){
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

}
