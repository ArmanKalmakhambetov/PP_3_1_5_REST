const urlAuth = "/api/users/auth";

getAuthUser()

getUserInfo()

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