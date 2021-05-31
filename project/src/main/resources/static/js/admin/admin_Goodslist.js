function changeStatus(manage_option, idlist){ //ajax로 처리;
    const json = JSON.stringify(idlist)
    console.log(json);
    switch(manage_option){
        case 'onsale': //수정 사항 있음.
            $.ajax({
                type: 'PATCH',
                url: '/admin/itemList1',
                data: json,
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
                url: '/admin/itemList2',
                data: json,
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
                url: '/admin/itemList3',
                data: json,
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