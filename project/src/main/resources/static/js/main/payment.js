$(function(){
    let total_price = 0
    let discount = 0
    const canuse = parseInt($('.canuse').val());
    const mileage_input = 'input[name=used_mileage]';

    // 초기 총 주문금액 및 결제 예정금액 산출
    $('.item_price').each(function(){
        let order_price = parseInt($(this).val()) * parseInt($(this).siblings('.item_quantity').val());
        $(this).closest('tr').find('.order_price').html(order_price.toLocaleString()); //한 아이템에 대한 총 가격 세팅
        $(this).closest('tr').find('.order_mileage').html((order_price/100).toLocaleString());
        total_price += order_price;
    })
    $('input[name=total_price]').val(total_price);
    $('#total').html(total_price.toLocaleString());
    $('input[name=tobepaid_price]').val(total_price)
    $('#tobepaid').html(total_price.toLocaleString());

    //체크박스 전체선택 or 해제
    $('.check_all').click(function(){
        if($('.check_all').is(":checked")){
            $('.check').prop('checked', true)
        }else {
            $('.check').prop('checked', false)
        }
    })
    
    //체크박스 선택 삭제
    $('.deletebtn').click(function(){
        $('.check').each(function(){
            if($(this).is(':checked')){
                let tr = $(this).closest('tr');
                total_price -= parseInt(tr.find('.item_price').val()) * parseInt(tr.find('.item_quantity').val()); 
                tr.remove();
                $('input[name=total_price]').val(total_price);
                $('#total').html(total_price);
                $('input[name=tobepaid_price]').val(total_price-discount);
                $('#tobepaid').html(total_price-discount);
            }
        })
        if($('.check').length == 0){    //상품이 전부 삭제되었을땐 메인화면으로 튕겨낸다.
            location.href = './index.html';
        }
    })
    
    // 마일리지 적용
    $(mileage_input).keyup(function(){
        discount = parseInt($(this).val() ? $(this).val() : 0);
        if(discount > canuse){
            discount = canuse;
        }
        if(discount <= total_price){    
            $('#discounted').html(discount.toLocaleString());
            $('input[name=used_mileage]').val(discount);
            $('#tobepaid').html((total_price-discount).toLocaleString());
            $('input[name=tobepaid_price]').val(total_price-discount);
        }
    })

    // 배송정보 선택 AJAX
    $('input[name=address_id]').click(function(){
        console.log($(this).val());
        if($(this).val() == 'new_addr'){
            $('#address_recipient').val('');
            $('#sample6_postcode').val('');
            $('#sample6_address').val('');
            $('#sample6_detailAddress').val('');
            $('.address_home_number:not(:first-child)').val('');
            $('.address_phone_number:not(:first-child)').val('');
        }else{

            let addr = $(this).val();
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url:"/main/payment/changeaddress/" + addr,
                type:'POST',
                // dataType: 'json',
                beforeSend : function(xhr)
                {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                    xhr.setRequestHeader(header, token);
                },
                success:function(address_obj){
                    console.log(address_obj)
                    console.log(typeof(address_obj))
                    $('#address_recipient').val(address_obj.recipient)
                    $('#sample6_postcode').val(address_obj.zipcode)
                    $('#sample6_address').val(address_obj.city)
                    $('#sample6_detailAddress').val(address_obj.street)
                    const homenumber = address_obj.addressHomePhoneNumber
                    const homenumberSplit = homenumber.split(',');
                    $("#address_home_number").val(homenumberSplit[0]).prop('selected', true)
                    $(".address_home_number").each(function(idx, item){
                        if(idx == 0){
                            return true
                        }
                        $(item).val(homenumberSplit[idx])
                    })

                    const phonenumber = address_obj.addressPhoneNumber
                    const phonenumberSplit = phonenumber.split(',');
                    $("#address_phone_number").val(phonenumberSplit[0]).prop('selected', true)
                    $(".address_phone_number").each(function(idx, item){
                        if(idx == 0){
                            return true
                        }
                        $(item).val(phonenumberSplit[idx])
                    })

                },
                error:function(e){
                    alert(e.responseText);
                }
            })        
        }
    })
    
})