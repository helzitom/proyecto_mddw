function login() {
    const usuario = document.getElementById("user").value;
    const password = document.getElementById("pass").value;

    if (usuario === "" || password === "") {
        alert("Completa todos los campos");
        return false;
    }

    const usuariosRegistrados = JSON.parse(localStorage.getItem("usuarios")) || [];

    const encontrado = usuariosRegistrados.find(u =>
        u.usuario === usuario && u.contraseña === password
    );

    if (!encontrado) {
        alert("Usuario o contraseña incorrectos");
        return false;
    }

    if (encontrado.estado === "bloqueado") {
        alert("Este usuario está bloqueado por el administrador");
        return false;
    }

    if (encontrado.rol === "admin") {
        alert("Bienvenido Administrador " + encontrado.usuario);
        window.location.href = "admin.html";
        return false;
    }

    alert("Bienvenido " + encontrado.usuario);
    window.location.href = "index.html";
    return false;
}