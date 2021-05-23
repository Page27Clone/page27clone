function deleteUser(id){
    $.ajax({
        type: 'DELETE',
        url: '/admin/userlist/' + id
    }).done(function(word){
        alert(word);
        window.location.href = '/admin/userlist';
    }).fail(function (error){
        alert(JSON.stringify(error));
    })
}

function deleteCheckedUsers(){
    let list = [];
    $('.checkbox-select').each(function(){
        if($(this).is(':checked')){
            let val1 = $(this).closest('tr').find('input[type=hidden]').val();
            list.push(val1)
        }
    })
    console.log(list)
        $.ajax({
            type: 'DELETE',
            url: '/admin/userlist2',
            data: {idList : list},
            traditional: true
            //contentType :   "application/x-www-form-urlencoded; charset=UTF-8"
        }).done(function(word){
            alert(word);
            window.location.href = '/admin/userlist';
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
}

function checkall(){
    $('.checkbox-select').prop('checked', true);
}

$(function(){
    $('.checkbox-selectall').click(function(){
        if($(this).is(':checked')){
            $('.checkbox-select').prop('checked', true);
        }else{
            $('.checkbox-select').prop('checked', false);
        }
    })
})
