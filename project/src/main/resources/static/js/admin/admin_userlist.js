function openUserDetail(id){ //유저 상세정보 페이지 open (컨트롤러 만들어야함)
    location.href='/admin/userList/user/' + id;
}

function deleteUser(id){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    if(confirm("해당 회원의 주문정보가 함께 삭제될 수 있습니다. 정말로 삭제하시겠습니까?")){
        $.ajax({
            type: 'DELETE',
            url: '/admin/userList/' + id,
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    }else{
        return false;
    }

}

function deleteCheckedUsers(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    let list = [];
    $('.checkbox-select').each(function(){
        if($(this).is(':checked')){
            let val1 = $(this).closest('tr').find('input[type=hidden]').val();
            list.push(val1)
        }
    })
    if(confirm("해당 회원의 주문정보가 함께 삭제될 수 있습니다. 정말로 삭제하시겠습니까?")){
        $.ajax({
            type: 'DELETE',
            url: '/admin/userList',
            data: {idList : list},
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            traditional: true
        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    }else{
        return false;
    }
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
