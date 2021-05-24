$(function(){ //ajax 수정필요
    $('.changestatusbtn').click(function(){
        const order_status = $(this).closest('tr').find('.omode').val();
        const id = $(this).closest('tr').find('input[type=hidden]').val();
        $.ajax({
            type: 'PATCH',
            url: '/admin/order1/' + id,
            data: {status : order_status}
        }).done(function(word){
            alert(word);
            window.location.href = '/admin/order';
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    })
})