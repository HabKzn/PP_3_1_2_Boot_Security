/**
 *
 *
 *
 */

$('document').ready(function () {
    $('.table .eBtn').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $.get(href, function (user, status){
           $('.myForm #nameEdit').val(user.username);
           $('.myForm #lastNameEdit').val(user.lastName);
           $('.myForm #ageEdit').val(user.age);
           $('.myForm #emailEdit').val(user.email);
           $('.myForm #passwordEdit').val(user.password);
        });


        $('.myForm #editModal').modal();

    });
});