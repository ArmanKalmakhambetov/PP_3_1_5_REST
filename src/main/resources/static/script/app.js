const url = "/api/users/";
const urlAuth = "/api/users/auth";
const urlRoles = "/api/users/roles";
const table = document.querySelector("#tbody");
let result = '';

const modalElement = new bootstrap.Modal(document.querySelector("#modalEdit"));
const modalDelete = new bootstrap.Modal(document.querySelector("#modalDelete"));

const formDelete = document.querySelector(".formDelete");
const formCreate = document.querySelector(".formCreate");

const formEdit = document.querySelector(".formEdit");

const ID1 = document.querySelector("#id");
const firstName1 = document.querySelector("#firstName");
const lastName1 = document.querySelector("#lastName");
const age1 = document.querySelector("#age");
const email1 = document.querySelector("#email");
const password1 = document.querySelector("#password");
let roles1 = document.querySelector("#roleSelection1");
let option = "";

const IDDelete = document.querySelector("#idDelete");
const firstNameDelete = document.querySelector("#firstNameDelete");
const lastNameDelete = document.querySelector("#lastNameDelete");
const ageDelete = document.querySelector("#ageDelete");
const emailDelete = document.querySelector("#emailDelete");
let rolesDelete = document.querySelector("#roleDelete");

const firstNameNew = document.querySelector("#firstNameNew");
const lastNameNew = document.querySelector("#lastNameNew");
const ageNew = document.querySelector("#ageNew");
const emailNew = document.querySelector("#emailNew");
const passwordNew = document.querySelector("#passwordNew");
let rolesNew = document.querySelector("#rolesNew");

const btnCreate = document.querySelector("#btnCreate");

async function getAllRoles() {
    const res = await fetch(urlRoles);
    const roles = await res.json();
    return roles.map(role => role.roleName);
}


getAuthUser();

async function getAuthUser() {
    await fetch(urlAuth)
        .then(res => res.json())
        .then(user => {
            let username = document.querySelector("#username");
            username.innerHTML = user.email;
            let roleForlabel = document.querySelector("#roleForLabel");
            let userRoles = "";
            for (let role of user.roles) {
                userRoles += role.roleName.substring(5) + " ";
            }
            roleForlabel.innerHTML = userRoles;
        })
}

getUserTable();

async function getUserTable(json) {
    await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                const roles = user.roles.map(role => role.roleName.replaceAll("ROLE_", "")).join(", ");
                result += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.lastname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    <td><a id="btnEdit" class="btnEdit btn btn-info text-white">Edit</a></td>
                    <td><a id="btnDelete" class="btnDelete btn btn-danger">Delete</a></td>
                </tr>
        `
            })
            table.innerHTML = result;
        })
        .catch(error => console.log(error))

}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e);
        }
    })
}


let idForm = 0;

on(document, 'click', '#btnDelete', e => {
    const row = e.target.parentNode.parentNode;
    idForm = row.children[0].innerHTML;
    const firstname = row.children[1].innerHTML;
    const lastname = row.children[2].innerHTML;
    const age = row.children[3].innerHTML;
    const email = row.children[4].innerHTML;
    const roles = row.children[5].innerHTML;
    IDDelete.value = idForm;
    firstNameDelete.value = firstname;
    lastNameDelete.value = lastname;
    ageDelete.value = age;
    emailDelete.value = email;

    rolesDelete.innerHTML = "";
    roles.split(", ").forEach(role => {
        const option = document.createElement("option");
        option.text = role;
        rolesDelete.add(option);
    });

    option = "delete";
    modalDelete.show();
})

let idForm1 = 0;
on(document, 'click', '#btnEdit', async e => {
    const row = e.target.parentNode.parentNode;
    idForm1 = row.children[0].innerHTML;
    const firstname = row.children[1].innerHTML;
    const lastname = row.children[2].innerHTML;
    const age = row.children[3].innerHTML;
    const email = row.children[4].innerHTML;
    ID1.value = idForm1;
    firstName1.value = firstname;
    lastName1.value = lastname;
    age1.value = age;
    email1.value = email;
    const allRoles = await getAllRoles();
    roles1.innerHTML = '';
    allRoles.forEach(role => {
        const option = document.createElement('option');
        option.text = role.replace('ROLE_', "");
        roles1.add(option);
    });

    option = 'edit';
    modalElement.show();

})


formEdit.addEventListener("submit", (e) => {
    e.preventDefault();
    if (option === "edit") {
        let UserRoles = [];
        for (let option of roles1.options) {
            if (option.selected) {
                UserRoles.push(option.value);
            }
        }
        fetch(url + ID1.value, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": firstName1.value,
                "lastname": lastName1.value,
                "age": age1.value,
                "email": email1.value,
                "password": password1.value,
                "roles": UserRoles
            })
        })
            .then(response => response.json())
            .then(() => {
                result = "";
                getUserTable();

            })

        modalElement.hide();
    }
})


formDelete.addEventListener('submit', (e) => {
    e.preventDefault(); // предотвращаем перезагрузку страницы
    if (option == "delete") {
        // console.log("delete")

        fetch(url + idForm, {
            method: "DELETE"
        })
            .then(data => {
                console.log(data)
                result = "";
                getUserTable();
                modalDelete.hide();
            })
    }
})

fillRoles();

async function fillRoles() {
    const allRoles = await getAllRoles();
    rolesNew.innerHTML = '';
    allRoles.forEach(role => {
        const option = document.createElement('option');
        option.text = role.replace('ROLE_', "");
        rolesNew.add(option);
    });
}

formCreate.addEventListener("submit", (e) => {
    e.preventDefault();


    let firstName = firstNameNew.value;
    let lastName = lastNameNew.value;
    let age = ageNew.value;
    let email = emailNew.value;
    let password = passwordNew.value;
    let roles = [];


    for (let option of rolesNew.options) {
        if (option.selected) {
            roles.push(option.value);
        }
    }


    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "username": firstName,
            "lastname": lastName,
            "age": age,
            "email": email,
            "password": password,
            "roles": roles
        })
    })
        .then(response => response.json())
        .then(() => {
            // очищаем поля формы
            firstNameNew.value = "";
            lastNameNew.value = "";
            ageNew.value = "";
            emailNew.value = "";
            passwordNew.value = "";
            result = "";
            getUserTable();

        })
        .catch(error => console.error("Error:", error));
});

getUserInfo();

async function getUserInfo() {
    await fetch(urlAuth, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(res => res.json())
        .then(authUser => {
            const idUser = document.querySelector("#IDUser")
            const firstNameUser = document.querySelector("#firstNameUser")
            const lastNameUser = document.querySelector("#lastNameUser")
            const ageUser = document.querySelector("#ageUser")
            const emailUser = document.querySelector("#emailUser")
            const roleUser = document.querySelector("#roleUser")

            idUser.innerHTML = authUser.id;
            firstNameUser.innerHTML = authUser.username;
            lastNameUser.innerHTML = authUser.lastname;
            ageUser.innerHTML = authUser.age;
            emailUser.innerHTML = authUser.email;
            roleUser.innerHTML = authUser.roles.map(role => role.roleName.replaceAll("ROLE_", "")).join(", ");
        })

}

