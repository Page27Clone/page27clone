function test(id){
    $.ajax({
        type: 'DELETE',
        url: '/admin/userlist/' + id
    }).done(function(word){
        alert(word);
        window.location.href = '/admin/userlist';
    }).fail(function (error){
        alert(JSON.stringify(error));
    })
}
