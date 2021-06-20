let price = 0 

function sendorder(){
    if($('form table tbody tr').length == 0){
        alert('상품 옵션을 선택해주세요');
        return false;
    }

    const itemIdx = $('input[name=item_idx]').val()
    const color = $('select[name=item_color]').val()
    const quantity = $('input[name=quantity]').val()
    const itemlist = [{idx : itemIdx, color, quantity}]

    $('input[name=itemlist]').val(JSON.stringify(itemlist))


}

function quantitychange(){ //함수는 가장 밖에서 선언해야 알아먹는다.
    const totalprice = price * parseInt($('input[name=quantity]').val());
    $('.price_sum').html(totalprice.toLocaleString());
    $('.totalprice').html(totalprice.toLocaleString());
}

$(function(){
    $('.select_color').on('change', function(){
        const product_name = $('.product_caption').html();
        const option_val = $(this).val();
        price = parseInt($('.price').val());

        if(option_val != '00'){
            $('form table tbody').children().remove();
            $('form table tbody').append('<tr>'+
            '<td>'+product_name+'<p>-'+option_val+'</p></td>'+
            '<td><input type="number" name="quantity" min="1" value="1" onchange="quantitychange()"></td>'+
            '<td class="td_price">₩ <span class="price_sum">'+price.toLocaleString()+'</span></td>'+
            '</tr>');
            $('.totalprice').html(price.toLocaleString());
        }
    })
})