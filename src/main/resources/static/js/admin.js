$(document).ready(function () {
    getPrincipal();
    getData();
});

var principal;
var allRoles;
var allUsers;

//получение principal
async function getPrincipal() {
    let jsonPrincipal = await fetch('/getinfo');

    if (jsonPrincipal.ok) {
        principal = await jsonPrincipal.json();
    }
    fillNavbar()
    usersTable();
}

//заполнение navbar сверху
function fillNavbar() {
    $('#nameNavbar').append(principal.username);
    for (let i = 0; i <= principal.roles.length - 1; i++) {
        $('#rolesNavbar').append(principal.roles[i].name)
        if (i != principal.roles.length - 1) {
            $('#principal-roles').append(", ");
        }
    }
}


<!--Верхняя полоска с кнопкой LOGOUT-->
async function getData() {
    let users = await fetch('/users');
    let roles = await fetch('/roles');

    if (users.ok) {
        allUsers = await users.json();
    }

    if (roles.ok) {
        allRoles = await roles.json();
    }
    usersTable();
}

//таблица с пользователями
function usersTable() {
    $('.user-table-body').empty();

    $.each(allUsers, function (i, user) {
        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.username),
            $('<td>').text(user.lastName),
            $('<td>').text(user.age),
            $('<td>').text(user.email),
            $('<td>').text(function () {
                let roles = "";
                for (let i = 0; i <= user.roles.length - 1; i++) {
                    roles += user.roles[i].name;
                    if (i !== user.roles.length - 1) {
                        roles += ", ";
                    }
                }
                return roles;
            }),
            $('<td>').append($('<button>').text("Edit").attr({
                "type": "button",
                "id": "buttonEdit",
                "class": "btn btn-info",
                "data-bs-toggle": "modal",
                "data-bs-target": "#editModal",
            }).data("user", user)),
            $('<td>').append($('<button>').text("Delete").attr({
                "type": "button",
                "id": "buttonDelete",
                "class": "btn btn-danger",
                "data-bs-toggle": "modal",
                "data-bs-target": "#deleteModal",
            }).data("user", user))
        ).appendTo('.user-table-body')
    })
}

//New User form
$(document).on('click', '#new-user-tab', function () {
    $(`#newUserRoles`).empty();
    $('#nameNewUser').empty();
    $('#surnameNewUser').empty();
    $('#ageNewUser').empty();
    $('#usernameNewUser').empty();
    $('#passwordNewUser').empty();

    $.each(allRoles, function(i, role) {
        $(`#newUserRoles`).append(
            $('<option>').text(role.name).attr({
                "id": role.id,
                "value": role.role,
            })
        )
    })
})

//Кнопка Add new User
$(document).on('click', '#submit-add', async function () {
    let roles = [];
    $('#newUserRoles option:selected').each(function(index, value) {
        roles[index] = {
            id: value.id,
            name: value.value
        }
    });

    const user = {
        username: $('#nameNewUser').val(),
        lastName: $('#surnameNewUser').val(),
        age: $('#ageNewUser').val(),
        email: $('#usernameNewUser').val(),
        password: $('#passwordNewUser').val(),
        roles: roles
    };

    try {
        const response = await fetch('/create', {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        $('#users-table-tab').click();
        await getData();


        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
    } catch (error) {
        console.error('Ошибка:', error);
    }
})

//Кнопка Delete
$(document).on('click', '#submit-delete', async function () {

    try {
        const response = await fetch('/delete/'+ $('#idToDelete').val(), {
            method: 'DELETE',
        });

        $('#button-close-delete-modal').click();
        await getData();

        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
    } catch (error) {
    }
})


//Модальное окно Edit
$(document).on('click', '#buttonEdit', function () {
    $(`#rolesToEdit`).empty()

    let user = $(this).data("user");
    $('#idToEdit').val(user.id)
    $('#nameToEdit').val(user.username)
    $('#surnameToEdit').val(user.lastName)
    $('#ageToEdit').val(user.age)
    $('#usernameToEdit').val(user.email)
    $('#passwordToEdit').val(user.password)

    let selected = false;
    $.each(allRoles, function(i, role) {
        $.each(user.roles, function (i, userRole) {
            if (userRole.name === role.name)
                selected = true;
        })

        $(`#rolesToEdit`).append(
            $('<option>').text(role.name).attr({
                "id": role.id,
                "value": role.name,
                "selected": selected,
            })
        )
        selected = false;
    })
})

//Кнопка edit
$(document).on('click', '#submit-edit', async function () {
    let roles = [];
    $('#rolesToEdit option:selected').each(function(index, value) {
        roles[index] = {
            id: value.id,
            name: value.value
        }
    });

    const user = {
        id: $('#idToEdit').val(),
        username: $('#nameToEdit').val(),
        lastName: $('#surnameToEdit').val(),
        age: $('#ageToEdit').val(),
        email: $('#usernameToEdit').val(),
        password: $('#passwordToEdit').val(),
        roles: roles
    };

    try {
        const response = await fetch('/update', {
            method: 'PATCH',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        $('#button-close-edit-modal').click();
        await getData();

    } catch (error) {
        console.error('Error:', error);
    }
})

//Модальное окно Delete
$(document).on('click', '#buttonDelete', function () {
    $(`#rolesToDelete`).empty()

    let user = $(this).data("user");
    $('#idToDelete').val(user.id)
    $('#nameToDelete').val(user.username)
    $('#surnameToDelete').val(user.lastName)
    $('#ageToDelete').val(user.age)
    $('#usernameToDelete').val(user.email)

    $.each(user.roles, function (i, userRole) {
        $(`#rolesToDelete`).append(
            $('<option>').text(userRole.name).attr({
                "id": userRole.id,
                "value": userRole.name,
                "selected": "true",
            })
        )
    })
})
