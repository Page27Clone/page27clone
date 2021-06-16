$(function(){
    $('.check_all').on('click', function(){
        if($(this).is(':checked')){
            $('.check').prop('checked', true);
        }else{
            $('.check').prop('checked', false);
        };
    });
    $('.deletebtn').on('click', function(){
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
            traditional: true,

        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    })
    
})