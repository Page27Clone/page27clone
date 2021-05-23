function test(id){
    let json_id = {testvalue : id}
    $.ajax({
        type: 'DELETE',
        url: '/admin/userdelete',
        data: JSON.stringify(json_id),
        contentType: 'application/json'
    }).done ( function(word){
        alert(word);
        window.location.href = '/admin/userlist';
    }).fail(function (error){
        alert(JSON.stringify(error));
    })
}
