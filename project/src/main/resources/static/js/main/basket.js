$(function(){
    //총 가격산출
    let total_price = 0;
    $('.item_price').each(function(){
        const tr = $(this).closest('tr');
        let order_price = parseInt($(this).val()) * parseInt($(this).closest('tr').find('.item_quantity').val());
        let order_mileage = order_price / 100;
        tr.find('.order_price').html(order_price.toLocaleString());
        tr.find('.order_mileage').html(order_mileage.toLocaleString());
        total_price += order_price;
    })
    $('#total').html(total_price.toLocaleString());
    $('#tobepaid').html(total_price.toLocaleString());

    //총 row수 산출
    let num_rows = $('.orderlist_table tbody tr').length;
    $('.num_orders').html(num_rows);

    //전체선택 or 전체해제
    $('.check_all').on('click', function(){
        if($(this).is(':checked')){
            $('.check').prop('checked', true);
        }else{
            $('.check').prop('checked', false);
        }
    });

    //수량변경
    $('.numberupdatebtn').on('click', function(){
        const basket_id = parseInt($(this).closest('tr').find('.basket_id').val());
        const item_quantity = parseInt($(this).siblings('.item_quantity').val());
        $.ajax({
            type: 'PATCH',
            url: '/main/basket/changequantity/' + basket_id + '/' + item_quantity
        }).done(function(word){
            alert(word);
            location.reload();

        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    })
    
    //optionbtn 클릭시
    $('.optionbtn').on('click', function(){
        const tr = $(this).closest('tr');
        const basket_id = parseInt(tr.find('.basket_id').val());
        const item_quantity = parseInt(tr.find('.item_quantity').val());
        console.log(basket_id, item_quantity);
        
        if($(this).hasClass('buyitbtn')){ //해당 아이템 주문
            const form = $('<form method="post"></form>')
            form.attr("action", "/main/buyitems")
            let itemlist = [];
            let id_quan = {id: basket_id, quantity:item_quantity}
            itemlist.push(id_quan)
            form.append($('<input>', {type: 'hidden', name: 'itemlist', value: JSON.stringify(itemlist)}));
            form.append($('<input>', {type: 'hidden', name: 'where', value: 'basket'})); //현재 넘어가는 정보는 basket페이지에서 넘어간다
            form.appendTo('body');
            form.submit();
        }else if($(this).hasClass('deleteitbtn')){ //해당 아이템 삭제
            console.log('삭제버튼눌림');
            $.ajax({
                type: 'DELETE',
                url: '/main/basket/remove/'+ basket_id
            }).done(function(word){
                alert(word);
                location.reload();
            }).fail(function(error){
                alert(JSON.stringify(error));
            })
        }
    })
    
    //선택아이템 삭제
    $('.deletebtn').on('click', function(){
        let itemlist = []
        $('.check').each(function(){
            if($(this).is(':checked')){
                const tr = $(this).closest('tr');
                itemlist.push(parseInt(tr.find('.basket_id').val()))
            }
        })
        console.log(itemlist);
        $.ajax({
            type: 'DELETE',
            url: '/main/basket/removeitems',
            data: {itemList : itemlist},
            traditional: true
        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    })

    //장바구니 비우기
    $('.basketclearbtn').on('click', function(){
        $.ajax({
            type: 'DELETE',
            url: '/main/basket/removeall'
        }).done(function(word){
            alert(word);
            location.reload();
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    })

    //전체, 선택 상품 주문
    $('.buymanybtn').on('click', function(){
        const form = $('<form method="post"></form>');
        form.attr("action", "/main/buyitems")
        let itemlist = [];
        const basket_id_list = $('.basket_id').get();
        const item_quantity_list = $('.item_quantity').get();
        
        if($(this).hasClass('buyallbtn')){
            for(let i=0 ; i<basket_id_list.length; i++){ //전체 상품주문
                let b_id = $(basket_id_list[i]).val(); //parseInt?
                let b_quantity = $(item_quantity_list[i]).val(); //parseInt?
                let id_quan = {id:b_id, quantity:b_quantity};
                itemlist.push(id_quan);
            }
        }else if($(this).hasClass('buyselectedbtn')){ //선택 상품주문
            for(let i=0 ; i<basket_id_list.length; i++){
                if($(basket_id_list[i]).closest('tr').find('.check').is(':checked') == false){
                    continue;
                }
                let b_id = $(basket_id_list[i]).val(); //parseInt?
                let b_quantity = $(item_quantity_list[i]).val(); //parseInt?
                let id_quan = {id:b_id, quantity:b_quantity};
                itemlist.push(id_quan);
            }
        }
        console.log(itemlist);
        form.append($('<input>', {type: 'hidden', name: 'itemlist', value: JSON.stringify(itemlist)}));
        form.append($('<input>', {type: 'hidden', name: 'where', value: 'basket'}));
        form.appendTo('body');
        form.submit();
    })

})