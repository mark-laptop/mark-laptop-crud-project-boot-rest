let modal = $('#defaultModal');
let modalTitle = $('.modal-title');
let modalBody = $('.modal-body');
let modalFooter = $('.modal-footer');

let primaryButton = $('<button type="button" class="btn btn-primary"></button>');
let dismissButton = $('<button type="button" class="btn btn-secondary" data-dismiss="modal"></button>');
let dangerButton = $('<button type="button" class="btn btn-danger"></button>');

$(document).ready(function () {
    viewAllUsers();
    defaultModal();
});

function defaultModal() {
    modal.modal({
        keyboard: true,
        backdrop: "static",
        show: false,
    }).on("show.bs.modal", function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let action = button.data('action');
        switch (action) {
            case 'addUser':
                addUser($(this));
                break;

            case 'editUser':
                editUser($(this), id);
                break;

            case 'deleteUser':
                deleteUser($(this), id);
                break;
        }
    }).on('hidden.bs.modal', function (event) {
        $(this).find('.modal-title').html('');
        $(this).find('.modal-body').html('');
        $(this).find('.modal-footer').html('');
    });
}

async function viewAllUsers() {
    $('#userTable tbody').empty();
    const usersResponse = await userService.findAll();
    const usersJson = usersResponse.json();
    usersJson.then(users => {
        users.forEach(user => {
            let roleRow = '';
            user.roles.forEach(role => {
                roleRow += role.name.replace('ROLE_', '') + ' ';
                let userRow = `$(<tr>
                        <th scope="row">${user.id}</th>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${roleRow}</td>
                        <td><button class="btn btn-info" data-id="${user.id}" data-action="editUser" data-toggle="modal" data-target="#defaultModal">Edit</button></td>
                        <td><button class="btn btn-danger" data-id="${user.id}" data-action="deleteUser" data-toggle="modal" data-target="#defaultModal">Delete</button></td>
                    </tr>)`;
                $('#userTable tbody').append(userRow);
            });
        });
    });
}

// async function addUser(modal) {
//     const rolesResponse = await roleService.findAll();
//     const rolesJson = rolesResponse.json();
//
//     modal.find(modalTitle).html('Add User');
//     let userFormHidden = $('.userForm:hidden')[0];
//     modal.find(modalBody).html($(userFormHidden).clone());
//     let userForm = modal.find('.userForm');
//     userForm.prop('id', 'addUserForm');
//     modal.find(userForm).show();
//     dismissButton.html('Cancel');
//     modal.find(modalFooter).append(dismissButton);
//     primaryButton.prop('id', 'saveUserButton');
//     primaryButton.html('Save');
//     modal.find(modalFooter).append(primaryButton);
//     rolesJson.then(roles => {
//         roles.forEach(role => {
//             modal.find('#roles').append(new Option(role.name, role.id));
//         });
//     });
//
//     $('#saveUserButton').click(async function (e) {
//         let id = userForm.find('#id').val().trim();
//         let nickname = userForm.find('#nickname').val().trim();
//         let firstName = userForm.find('#firstName').val().trim();
//         let lastName = userForm.find('#lastName').val().trim();
//         let age = userForm.find('#age').val().trim();
//         let password = userForm.find('#password').val().trim();
//         let email = userForm.find('#email').val().trim();
//         let roleId = userForm.find('#role option:selected').val().trim();
//         let data = {
//             id: id,
//             nickname: nickname,
//             firstName: firstName,
//             lastName: lastName,
//             age: age,
//             password: password,
//             email: email,
//             roles: {
//                 id: roleId
//             }
//         };
//
//         const userResponse = await userService.add(data);
//
//         if (userResponse.status == 201) {
//             viewAllUsers();
//             modal.find('.modal-title').html('Success');
//             modal.find('.modal-body').html('User added!');
//             dismissButton.html('Close');
//             modal.find(modalFooter).html(dismissButton);
//             $('#defaultModal').modal('show');
//         } else if (userResponse.status == 400) {
//             userResponse.json().then(response => {
//                 response.validationErrors.forEach(function (error) {
//                     modal.find('#' + error.field).addClass('is-invalid');
//                     modal.find('#' + error.field).next('.invalid-feedback').text(error.message);
//                 });
//             });
//         } else {
//             userResponse.json().then(response => {
//                 let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
//                         ${response.error}
//                         <button type="button" class="close" data-dismiss="alert" aria-label="Close">
//                             <span aria-hidden="true">&times;</span>
//                         </button>
//                     </div>`;
//                 modal.find('.modal-body').prepend(alert);
//             });
//         }
//     });
// }

async function editUser(modal, id) {
    const userResponse = await userService.findById(id);
    const userJson = userResponse.json();
    const rolesResponse = await roleService.findAll();
    const rolesJson = rolesResponse.json();

    let idInput = `<div class="form-group">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" disabled>
            <div class="invalid-feedback"></div>
        </div>`;

    modal.find(modalTitle).html('Edit User');
    let userFormHidden = $('.userForm:hidden')[0];
    modal.find(modalBody).html($(userFormHidden).clone());
    let userForm = modal.find('.userForm');
    userForm.prop('id', 'updateUserForm');
    modal.find(userForm).prepend(idInput);
    modal.find(userForm).show();
    dismissButton.html('Close');
    modal.find(modalFooter).append(dismissButton);
    primaryButton.prop('id', 'updateUserButton');
    primaryButton.html('Edit');
    modal.find(modalFooter).append(primaryButton);

    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#nickname').val(user.nickname);
        modal.find('#firstName').val(user.firstName);
        modal.find('#lastName').val(user.lastName);
        modal.find('#age').val(user.age);
        modal.find('#email').val(user.email);
        modal.find('#password').val(user.password);
        rolesJson.then(roles => {
            roles.forEach(role => {
                let flag = false;
                user.roles.forEach(roleUser => {
                    if (roleUser.id == role.id) {
                        flag = true;
                    }
                });
                if (flag)
                    modal.find('#roles').append(new Option(role.name.replace('ROLE_', ''), role.id, false, true));
                else
                    modal.find('#roles').append(new Option(role.name.replace('ROLE_', ''), role.id));
            });
        });
    });


    $('#updateUserButton').click(async function (e) {
        let id = userForm.find('#id').val().trim();
        let nickname = userForm.find('#nickname').val().trim();
        let firstName = userForm.find('#firstName').val().trim();
        let lastName = userForm.find('#lastName').val().trim();
        let age = userForm.find('#age').val().trim();
        let password = userForm.find('#password').val().trim();
        let email = userForm.find('#email').val().trim();
        let roleId = userForm.find('#roles option:selected').val().trim();
        let data = {
            id: id,
            nickname: nickname,
            firstName: firstName,
            lastName: lastName,
            age: age,
            password: password,
            email: email,
            roles: [
                {id: roleId}
            ]
        };

        const userResponse = await userService.update(id, data);

        if (userResponse.status == 200) {
            viewAllUsers();
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('User updated!');
            dismissButton.html('Close');
            modal.find(modalFooter).html(dismissButton);
            $('#defaultModal').modal('show');
        } else if (userResponse.status == 400) {
            userResponse.json().then(response => {
                response.validationErrors.forEach(function (error) {
                    modal.find('#' + error.field).addClass('is-invalid');
                    modal.find('#' + error.field).next('.invalid-feedback').text(error.message);
                });
            });
        } else {
            userResponse.json().then(response => {
                let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
                        ${response.error}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>`;
                modal.find('.modal-body').prepend(alert);
            });
        }
    });
}

async function deleteUser(modal, id) {
    const userResponse = await userService.findById(id);
    const userJson = userResponse.json();

    let idInput = `<div class="form-group">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" disabled>
            <div class="invalid-feedback"></div>
        </div>`;

    modal.find(modalTitle).html('Delete User');
    let message = '<strong>Are you sure to delete the following user?</strong>';
    modal.find(modalBody).html(message);
    let userFormHidden = $('.userForm:hidden')[0];
    modal.find(modalBody).append($(userFormHidden).clone());
    let userForm = modal.find('.userForm');
    userForm.prop('id', 'updateUserForm');
    modal.find(userForm).prepend(idInput);
    modal.find(userForm).show();
    dismissButton.html('Close');
    modal.find(modalFooter).append(dismissButton);
    dangerButton.prop('id', 'deleteUserButton');
    dangerButton.html('Delete');
    modal.find(modalFooter).append(dangerButton);

    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#nickname').val(user.nickname);
        modal.find('#firstName').val(user.firstName);
        modal.find('#lastName').val(user.lastName);
        modal.find('#age').val(user.age);
        modal.find('#password').val(user.password);
        modal.find('#email').val(user.email);
        user.roles.forEach(roleUser => {
            modal.find('#roles').append(new Option(roleUser.name.replace('ROLE_', ''), roleUser.id));
        });
    });

    $('#deleteUserButton').click(async function (e) {
        const userResponse = await userService.delete(id);

        if (userResponse.status == 204) {
            viewAllUsers();
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('User deleted!');
            dismissButton.html('Close');
            modal.find(modalFooter).html(dismissButton);
            $('#defaultModal').modal('show');
        } else {
            userResponse.json().then(response => {
                let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
                            ${response.error}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
                modal.find('.modal-body').prepend(alert);
            });
        }
    });
}

const http = {
    fetch: async function (url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });

        return response;
    }
};

const userService = {
    findAll: async () => {
        return await http.fetch('/api/v1/users');
    },
    add: async (data) => {
        return await http.fetch('/api/v1/users/create', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    findById: async (id) => {
        return await http.fetch('/api/v1/users/' + id);
    },
    update: async (id, data) => {
        return await http.fetch('/api/v1/users/update', {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },
    delete: async (id) => {
        return await http.fetch('/api/v1/users/delete/' + id, {
            method: 'DELETE'
        });
    },
};

const roleService = {
    findAll: async () => {
        return await http.fetch('/api/v1/roles');
    }
};