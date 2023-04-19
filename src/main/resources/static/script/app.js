getAuthUser();

async function getAuthUser () {
    await fetch("http://localhost:8080/api/users/auth")
        .then(res => res.json())
        .then(user => {
            let username = document.getElementById("username");
            username.textContent = user.username;
            let role = document.getElementById("role");
            role.textContent = user.role.name;
        })
}