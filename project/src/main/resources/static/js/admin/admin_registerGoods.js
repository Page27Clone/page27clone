function sendToServer(){ //기본정보 작성 안되어 있을 시 튕겨내기
    if($('.cmode1').val() == ''){
        alert('상품 분류를 선택해주세요');
        return false;
    }
    if($('input[name=item_name]').val() == ''){
        alert('상품명을 입력해주세요');
        return false;
    }
    if($('input[name=price]').val() == ''){
        alert('판매가격을 입력해주세요');
        return false;
    }
    if($('input[type=file]').val() == ''){
        alert('파일 이미지를 업로드해주세요');
        return false;
    }
}

$(function(){
    // 1차 옵션 변경시 2차옵션 드롭
    const cmode1 = $('.cmode1');
    cmode1.change(function(){
        $('.cmode2').empty();
        switch(cmode1.val()){
            case 'outer': 
                options = '<option value="jacket">자켓/점퍼</option>'+
                '<option value="cardigan">가디건</option>'+
                '<option value="coat">코트</option>'+
                '<option value="vest">조끼</option>'+
                '<option value="suit">수트</option>';
                break;
            case 'top':
                options = '<option value="longsleeve">긴팔</option>'+
                '<option value="shortsleeve">반팔</option>'+
                '<option value="knit">니트</option>'+
                '<option value="sleeveless">나시</option>'+
                '<option value="threequartersleeve">7부</option>';
                break;
            case 'shirts':
                options = '<option value="basic">베이직</option>'+
                '<option value="checkpattern">체크/패턴</option>'+
                '<option value="stripe">스트라이프</option>';
                break;
            case 'bottom':
                options = '<option value="cotton">면바지</option>'+
                '<option value="denim">데님</option>'+
                '<option value="slacks">슬랙스</option>'+
                '<option value="shorts">반바지</option>';
                break;
            case 'acc':
                options = '<option value="cap">CAP</option>'+
                '<option value="socks">SOCKS</option>'+
                '<option value="scarf">SCARF&MUFFLER</option>'+
                '<option value="tie">TIE&HANDKERCHIEF</option>'+
                '<option value="jewelry">JEWELRY</option>'+
                '<option value="bag">BAG</option>'+
                '<option value="belt">BELT</option>';
                break;
            default:
                options = '<option>2차 분류</option>';
        }
        $('.cmode2').append(options);
    })

    //파일업로드시 썸네일
    $('#upload_image').change(function(e){
    const images = e.target.files
    $('.img-box').empty();
    for(let i=0 ; i<images.length; i++){
        const Reader = new FileReader();
        Reader.readAsDataURL(images[i]);
        Reader.onload = function(){
            const img = '<img src="'+ Reader.result +'" alt="사진">';
            $('.img-box').append(img);
        }
    }
        
    })
})

