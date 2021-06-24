$(function(){
    $('.check_all').on('click', function(){
        if($(this).is(':checked')){
            $('.check').prop('checked', true);
        }else{
            $('.check').prop('checked', false);
        };
    });
    $('.deletebtn').on('click', function(){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let addresslist = [];
        $('.check').each(function(){
            if($(this).is(':checked')){
                addresslist.push(parseInt($(this).closest('tr').find('.address_key').val()));
            };
        });
        $.ajax({
            type: 'DELETE',
            url: '/main/address/delete',
            data: {addressIdList: addresslist},
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            traditional: true,

        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    })
    
})