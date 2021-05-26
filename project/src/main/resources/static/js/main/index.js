$(function(){
    //main carousel
    let count = 2 //타이머가 페이지 로드후 3초후 실행되기 때문에 3초후 페이지를 바로 2페이지로 바꿔주기위함
    $('input[type=radio]').click(function(){
        count = parseInt($(this).attr('id').substr(-1,1));
    })
    const mainCarousel_timer = setInterval(function(){
        $('#radio'+count).prop('checked', true);
        count = count%5+1;
    }, 3000);

    $('.fa-chevron-left').click(function(){
        $('input[type=radio]').each(function(){
            if($(this).is(':checked')){
                count = parseInt($(this).attr('id').substr(-1, 1))-1;
                if(count == 0){
                    count = 5
                }
                return false;
            }
        })
        $('#radio'+count).prop('checked', true);
    })

    $('.fa-chevron-right').click(function(){
        $('input[type=radio]').each(function(){
            if($(this).is(':checked')){
                count = parseInt($(this).attr('id').substr(-1, 1)) + 1;
                if(count == 6){
                    count = 1
                }
                return false;
            }
        })
        $('#radio'+count).prop('checked', true);
    })
    


    //weeklybest carousel
    const wbc = $('.weeklybest_carousel').get();
    const wbc_length = wbc.length;
    const menu = $('.weeklybest_menu span').get();
    let wbc_idx = 0;
    let menu_idx = 0
    $('.weeklybest_menu span').each(function(){
        $(this).attr('menu_num', menu_idx);
        menu_idx += 1
    }) 


    $('.weeklybest_menu span').on('click', function(){
        wbc_idx = parseInt($(this).attr('menu_num'));

        for(let i = 0; i < wbc_length; i++){
            if(i == wbc_idx){
                $(this).addClass('active');
                $(wbc[i]).show();
                $(wbc[i]).css('opacity', '1');
            }else{
                $(menu[i]).removeClass('active');
                $(wbc[i]).hide();
            }
        }
    }) 

    let wbc_timer = setInterval(function(){
        for(let i = 0; i < wbc_length; i++){
            if(i == wbc_idx){
                $(menu[i]).addClass('active');
                $(wbc[i]).show();
                $(wbc[i]).css('opacity', '1');
            }else{
                $(menu[i]).removeClass('active');
                $(wbc[i]).hide();
                $(wbc[i]).css('opacity', '0');
            }
        }
        wbc_idx = (wbc_idx + 1) % wbc_length;
    }, 3000);

    //weeklybest의 항목 hover시 나오는 buy it now, 장바구니 아이콘 클릭시 이벤트 전파 막기.
    $('.hoverbtn').on('click', function(e){
        console.log(e);
        e.preventDefault(); // 상위항목이 a 태그이므로 stopPropagation으로는 이벤트 전파를 막을 수 없다. preventDefault를 사용해서 이벤트 자체를 취소시켜야함. 
    })
})