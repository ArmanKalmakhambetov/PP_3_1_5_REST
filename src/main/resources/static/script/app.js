

getAuthUser();


const getAllUsers = "/api/users";
const getAuthUsers = "/api/users/auth";
const getUsers = "/api/users";
const getUser = "/api/users/{id}";
const table = document.querySelector("#tbody");
let result = '';


const formDelete = document.querySelector(".formDelete");
const formCreate = document.querySelector(".formCreate");

const formEdit = document.querySelector(".formEdit");
const modalElement = new bootstrap.Modal(document.querySelector("#modalEdit"));
const ID1 = document.querySelector("#id");
const firstName1 = document.querySelector("#firstName");
const lastName1 = document.querySelector("#lastName");
const age1 = document.querySelector("#age");
const email1 = document.querySelector("#email");
const password1 = document.querySelector("#password");
let roles1 = document.querySelector("#roleSelection1");
let option = "";


const modalDelete = new bootstrap.Modal(document.querySelector("#modalDelete"));
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
let rolesNew = document.querySelector("#roleNew");

const btnCreate = document.querySelector("#btnCreate");

async function getAllRoles() {
    const res = await fetch('/api/users/roles');
    const roles = await res.json();
    return roles.map(role => role.roleName);
}

btnCreate.addEventListener('click', () => {
    firstNameNew.value = '';
    lastNameNew.value = '';
    ageNew.value = '';
    emailNew.value = '';
    const allRoles = getAllRoles();
    rolesNew.innerHTML = '';
    allRoles.forEach(role => {
        const option = document.createElement('option');
        option.text = role.replace('ROLE_', "");
        rolesNew.add(option);
    });

    option = 'create'
})

console.log(typeof getAllRoles())





async function getAuthUser() {
    await fetch("/api/users/auth")
        .then(res => res.json())
        .then(user => {
            let username = document.querySelector("#username");
            username.innerHTML = user.email;
            let roleForlabel = document.querySelector("#roleForLabel");
            let userRoles = "";
            for (let role of user.authorities) {
                userRoles += role.authority.substring(5) + " ";
            }
            roleForlabel.innerHTML = userRoles;
        })
}

fetch("/api/users")
    .then(res => res.json())
    .then(users => show(users))
    .catch(error => console.log(error));

const show = (users) => {
    users.forEach(user => {
        const roles = user.authorities.map(role => role.authority.replaceAll("ROLE_", "")).join(", ");
        console.log(roles)
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
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e=> {
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
    const roles = row.children[5].innerHTML;
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



formEdit.addEventListener('submit', (e) => {
    e.preventDefault();
    if (option === 'edit') {

        let editRoles = [];
        for (let i = 0; i < roles1.options.length; i++) {
            if (roles1.options[i].selected) {
                editRoles.push({id: roles1.options[i].value});
            }
        }

        fetch("api/users/" + idForm1 , {
            method : "PUT",
            headers : {
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({
                id : ID1.value,
                firstName : firstName1.value,
                lastName : lastName1.value,
                age : age1.value,
                email : email1.value,
                password : password1.value,
                roles : editRoles
            })
        })
            .then(res => res.json())
            .then(() => location.reload())
    }
    modalElement.hide();
})

formCreate.addEventListener('submit', (e) => {
    if (option == "create") {
        // console.log("create")
        fetch("/api/users", {
            method : "POST",
            headers : {
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({
                firstNameNew : firstNameNew.value,
                lastNameNew : lastNameNew.value,
                ageNew : ageNew.value,
                emailNew : emailNew.value,
                passwordNew : passwordNew.value,
                rolesNew : rolesNew.value
            })
        })
            .then(res => res.json())
            .then(data => {
                const newUser = [];
                newUser.push(data);
                show(newUser)
            })
    }
})

formDelete.addEventListener('submit', (e) => {
    if (option == "delete") {
        // console.log("delete")

        fetch("/api/users/" + idForm, {
            method : "DELETE"
        })
            .then(res => res.json())
            .then(() => location.reload())
    }
    modalElement.hide();
})
