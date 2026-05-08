document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const usuario = document.getElementById("usuario").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const pass1 = document.getElementById("password").value;
        const pass2 = document.getElementById("password2").value;
        const terms = document.getElementById("terms").checked;
        const rol = document.getElementById("rol").value;

        const mayus = /[A-Z]/;
        const minus = /[a-z]/;
        const numero = /[0-9]/;

        if (pass1.length < 8) {
            alert("La contraseña debe tener mínimo 8 caracteres");
            return;
        }

        if (!mayus.test(pass1)) {
            alert("La contraseña debe contener al menos una letra MAYÚSCULA");
            return;
        }

        if (!minus.test(pass1)) {
            alert("La contraseña debe contener al menos una letra minúscula");
            return;
        }

        if (!numero.test(pass1)) {
            alert("La contraseña debe incluir al menos un número");
            return;
        }

        if (pass1 !== pass2) {
            alert("Las contraseñas no coinciden");
            return;
        }

        if (!terms) {
            alert("Debes aceptar los términos y condiciones");
            return;
        }

        if (rol === "") {
            alert("Debe seleccionar un tipo de cuenta");
            return;
        }

        let usuarios = JSON.parse(localStorage.getItem("usuarios")) || [];

        const existe = usuarios.some(u =>
            u.usuario === usuario || u.correo === correo
        );

        if (existe) {
            alert("El usuario o correo ya están registrados");
            return;
        }

        const nuevoUsuario = {
            usuario: usuario,
            correo: correo,
            contraseña: pass1,
            rol: rol,
            estado: "activo"
        };

        usuarios.push(nuevoUsuario);
        localStorage.setItem("usuarios", JSON.stringify(usuarios));

        alert("Registro exitoso");
        form.reset();
        window.location.href = "acceso.html";
    });
});