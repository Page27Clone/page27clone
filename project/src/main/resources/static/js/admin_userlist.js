function openUserDetail(id){ //유저 상세정보 페이지 open (컨트롤러 만들어야함)
    location.href='/admin/userlist/user/' + id;
}

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
            url: '/admin/userlist',
            data: {idList : list},
            traditional: true
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
