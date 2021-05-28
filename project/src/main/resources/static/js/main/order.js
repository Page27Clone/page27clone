$(function(){
    $('.period_item').click(function(){
        let now = new Date();
        let now_temp = now.getFullYear() + '-' + ('0'+(1+now.getMonth())).slice(-2) + '-' + ('0'+now.getDate()).slice(-2);
        
        let weekago = new Date(now);
        weekago.setDate(weekago.getDate()-7);
        let weekago_temp = weekago.getFullYear() + '-' + ('0'+(1+weekago.getMonth())).slice(-2) + '-' + ('0'+weekago.getDate()).slice(-2);

        let monthago = new Date(now);
        monthago.setDate(monthago.getDate()-30);
        let monthago_temp = monthago.getFullYear() + '-' + ('0'+(1+monthago.getMonth())).slice(-2) + '-' + ('0'+monthago.getDate()).slice(-2);

        let threemonthago = new Date(now);
        threemonthago.setDate(threemonthago.getDate()-90);
        let threemonthago_temp = threemonthago.getFullYear() + '-' + ('0'+(1+threemonthago.getMonth())).slice(-2) + '-' + ('0'+threemonthago.getDate()).slice(-2);

        let sixmonthago = new Date(now);
        sixmonthago.setDate(sixmonthago.getDate()-180);
        let sixmonthago_temp = sixmonthago.getFullYear() + '-' + ('0'+(1+sixmonthago.getMonth())).slice(-2) + '-' + ('0'+sixmonthago.getDate()).slice(-2);



        switch($(this).attr('days')){
            case '0':
                $('.firstdate').val(now_temp);
                break;
            case '7':
                $('.firstdate').val(weekago_temp);
                break;
            case '30':
                $('.firstdate').val(monthago_temp);
                break;
            case '90':
                $('.firstdate').val(threemonthago_temp);
                break;
            case '180':
                $('.firstdate').val(sixmonthago_temp);
                break;
        }
        $('.lastdate').val(now_temp);
    })
})