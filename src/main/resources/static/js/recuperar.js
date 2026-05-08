document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const usuario = document.getElementById("usuario").value.trim();
        const nueva = document.getElementById("password").value;
        const confirmar = document.getElementById("password-confirm").value;

        if (usuario === "" || nueva === "" || confirmar === "") {
            alert("Completa todos los campos");
            return;
        }

        if (nueva !== confirmar) {
            alert("Las contraseñas no coinciden");
            return;
        }

        let usuarios = JSON.parse(localStorage.getItem("usuarios")) || [];

        const index = usuarios.findIndex(u => u.usuario === usuario);

        if (index === -1) {
            alert("El usuario no existe ❌");
            return;
        }

        usuarios[index].contraseña = nueva;
        localStorage.setItem("usuarios", JSON.stringify(usuarios));

        alert("Contraseña actualizada correctamente ✔");
        window.location.href = "acceso.html";
    });

});