$(function(){
    //side-bar toggle
    $('.menu-dropdown').click(function(){
        $(this).children('.dropdown').slideToggle();
    })
    $('.dropdown').click(function(e){
        e.stopPropagation();
    })
});
