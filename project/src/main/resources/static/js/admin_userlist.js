$(function(){

    //총 검색 row 수 구현
    let table = '.user-table';
    let totalRows = $(table + ' tbody tr').length;
    $('.num-searched').html(totalRows);

    //페이징 구현
    let trnum = 0;
    let maxRows = 5;
    $(table + ' tbody tr').each(function(){
        trnum++;
        if(trnum > maxRows){
            $(this).hide();
        }
    })
    let pagenums = Math.ceil(totalRows/maxRows);
    $('.pagenation').html('');
    for(let i=1; i<=pagenums; i++){
        $('.pagenation').append('<li class="pagenatebtn" data-page="'+i+'"><a href="#">'+i+'</a></li>');
    }

    $('.pagenation li').on('click', function(){
        let pageNum = parseInt($(this).attr('data-page'));
        let firstnum = maxRows * (pageNum - 1) + 1
        let lastnum = firstnum + maxRows - 1
        trnum = 0;
        $(table + ' tbody tr').each(function(){
            trnum++;
            if(trnum >= firstnum && trnum <= lastnum){
                $(this).show();
            }else{
                $(this).hide();
            }
        })
    })

    //checkbox 전체 선택 and 해제
    $('.checkbox-selectall').on('click', function(){
        let checked = $(this).prop('checked')
        $('.checkbox-select').prop('checked', checked);
    })

    $('.selectallbtn').on('click', function(){
        $('input[type=checkbox]').prop('checked', true);
    })

})