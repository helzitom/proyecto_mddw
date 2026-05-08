
document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tabla-usuarios");
    let usuarios = JSON.parse(localStorage.getItem("usuarios")) || [];

    let hayCambios = false;
    usuarios.forEach(u => {
        if (!u.rol) {
            u.rol = "comprador";
            hayCambios = true;
        }
    });

    if (hayCambios) {
        localStorage.setItem("usuarios", JSON.stringify(usuarios));
    }

    function actualizarEstadisticas() {
        document.getElementById("stat-total").textContent = usuarios.length;
        document.getElementById("stat-activos").textContent = usuarios.filter(u => u.estado === "activo").length;
        document.getElementById("stat-bloqueados").textContent = usuarios.filter(u => u.estado === "bloqueado").length;
    }

    function mostrarUsuarios() {
        tabla.innerHTML = "";
        actualizarEstadisticas();

        usuarios.forEach((u, index) => {
            let fila = `
                <tr>
                    <td>${u.usuario}</td>
                    <td>${u.rol}</td>
                    <td>${u.estado}</td>
                    <td>
                        <button
                            class="${u.estado === "activo" ? "btn-bloquear" : "btn-desbloquear"}"
                            onclick="window.toggleEstado(${index})">
                            ${u.estado === "activo" ? "Bloquear" : "Desbloquear"}
                        </button>
                        <button class="btn btn-danger btn-sm ms-1" onclick="window.eliminarUsuario(${index})">X</button>
                    </td>
                </tr>
            `;
            tabla.innerHTML += fila;
        });
    }

    window.toggleEstado = function(index) {
        const estadoActual = usuarios[index].estado;
        const nuevoEstado = estadoActual === "activo" ? "bloqueado" : "activo";

        if (nuevoEstado === "bloqueado") {
            const motivo = prompt(`Ingrese el motivo para bloquear al usuario ${usuarios[index].usuario}:`);
            if (motivo === null || motivo.trim() === "") {
                alert("Bloqueo cancelado. Debe especificar un motivo.");
                return;
            }
        }

        usuarios[index].estado = nuevoEstado;
        localStorage.setItem("usuarios", JSON.stringify(usuarios));
        mostrarUsuarios();
    };

    window.eliminarUsuario = function(index) {
        const nombreUsuario = usuarios[index].usuario;
        const motivo = prompt(`Ingrese el motivo para eliminar permanentemente al usuario ${nombreUsuario}:`);

        if (motivo === null || motivo.trim() === "") {
            alert("Eliminación cancelada. Debe especificar un motivo.");
            return;
        }

        if (confirm(`¿Está seguro de ELIMINAR a ${nombreUsuario}?\nMotivo: ${motivo}`)) {
            usuarios.splice(index, 1);
            localStorage.setItem("usuarios", JSON.stringify(usuarios));
            mostrarUsuarios();
            alert(`El usuario ${nombreUsuario} ha sido eliminado. Motivo: ${motivo}`);
        }
    };

    mostrarUsuarios();
});