function changeStatus(manage_option, idlist){ //ajax로 처리;
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    const json = JSON.stringify(idlist)
    console.log(json);
    switch(manage_option){
        case 'onsale': //수정 사항 있음.
            $.ajax({
                type: 'PATCH',
                url: '/admin/itemList/onsale',
                data: json,
                beforeSend : function(xhr)
                {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                    xhr.setRequestHeader(header, token);
                },
                contentType : "application/json; charset=UTF-8"
            }).done(function(word){
                alert(word);
                location.reload();
            }).fail(function (error){
                alert(JSON.stringify(error));
            })
            break;
        case 'soldout':
            $.ajax({
                type: 'PATCH',
                url: '/admin/itemList/soldout',
                data: json,
                beforeSend : function(xhr)
                {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                    xhr.setRequestHeader(header, token);
                },
                contentType : "application/json; charset=UTF-8"
            }).done(function(word){
                alert(word);
                location.reload();
            }).fail(function (error){
                alert(JSON.stringify(error));
            })
            break;
        case 'deletegoods':
            $.ajax({
                type: 'DELETE',
                url: '/admin/itemList/remove',
                data: json,
                beforeSend : function(xhr)
                {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                    xhr.setRequestHeader(header, token);
                },
                contentType : "application/json; charset=UTF-8"
            }).done(function(word){
                alert(word);
                location.reload();
            }).fail(function (error){
                alert(JSON.stringify(error));
            })
            break;
    }
}

$(function(){
    //체크박스 전체선택 or 해제
    $('.checkbox-selectall').click(function(){
        if($(this).is(':checked')){
            $('.checkbox-select').prop('checked', true);
        }else{
            $('.checkbox-select').prop('checked', false);
        }
    })


    //처리 버튼 클릭시 옵션에 따라 ajax호출
    $('#managebtn').click(function(){
        const manage_option = $('.manage_option').val();
        let idlist = [];
        $('.checkbox-select').each(function(){
            if($(this).is(':checked')){
                const idx = $(this).closest('tr').find('.item_idx').val();
                const color = $(this).closest('tr').find('.item_color').val();
                const id_color = {itemIdx:idx, itemColor:color}
                idlist.push(id_color);
            }
        })
        changeStatus(manage_option, idlist);
    })
})