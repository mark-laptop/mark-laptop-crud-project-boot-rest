$(document).ready(function () {
    viewAllUsers();
});

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
                    </tr>)`;
                $('#userTable tbody').append(userRow);
            });
        });
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
    }
};