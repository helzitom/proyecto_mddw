document.addEventListener("DOMContentLoaded", () => {

    // =========================
    // ESTADO
    // =========================
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    // =========================
    // GUARDAR
    // =========================
    function guardarCarrito() {
        localStorage.setItem("carrito", JSON.stringify(carrito));
    }

    // =========================
    // AGREGAR
    // =========================
    function agregarAlCarrito(nombre, precio, imagen) {
        precio = Number(precio);

        let existente = carrito.find(p => p.nombre === nombre);

        if (existente) {
            existente.cantidad += 1;
        } else {
            carrito.push({ nombre, precio, imagen, cantidad: 1 });
        }

        guardarCarrito();
        mostrarCarrito();

        alert("Agregado: " + nombre);
    }

    // =========================
    // MOSTRAR
    // =========================
    function mostrarCarrito() {
        const lista = document.getElementById("carrito-lista");
        const totalSpan = document.getElementById("total");

        if (!lista || !totalSpan) return;

        lista.innerHTML = "";
        let total = 0;

        carrito.forEach((item, index) => {
            let subtotal = item.precio * item.cantidad;
            total += subtotal;

            lista.innerHTML += `
                <tr>
                    <td><img src="${item.imagen}" width="60" class="rounded"></td>
                    <td>${item.nombre}</td>
                    <td>S/ ${item.precio.toFixed(2)}</td>
                    <td>${item.cantidad}</td>
                    <td><b>S/ ${subtotal.toFixed(2)}</b></td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${index})">
                            Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });

        totalSpan.textContent = "S/ " + total.toFixed(2);
    }

    // =========================
    // ELIMINAR
    // =========================
    function eliminarProducto(i) {
        carrito.splice(i, 1);
        guardarCarrito();
        mostrarCarrito();
    }

    // =========================
    // VACIAR
    // =========================
    function vaciarCarrito() {
        carrito = [];
        guardarCarrito();
        mostrarCarrito();
    }

    // =========================
    // BOLETA
    // =========================
    function generarNumeroBoleta() {
        const d = new Date();
        return (
            d.getFullYear() +
            ("0" + (d.getMonth() + 1)).slice(-2) +
            ("0" + d.getDate()).slice(-2) +
            "-" +
            Math.floor(Math.random() * 9000 + 1000)
        );
    }

    function generarBoleta() {

        if (carrito.length === 0) {
            alert("Tu carrito está vacío");
            return;
        }

        const boleta = {
            fecha: new Date().toLocaleString(),
            numero: generarNumeroBoleta(),
            productos: carrito
        };

        localStorage.setItem("boletaDatos", JSON.stringify(boleta));

        localStorage.removeItem("carrito");

        window.location.href = "/boleta";
    }

    // =========================
    // EVENTOS PRODUCTOS
    // =========================
    document.querySelectorAll(".agregar-carrito").forEach(btn => {
        btn.addEventListener("click", function () {

            const prod = this.closest(".producto");

            if (!prod) {
                console.error("No se encontró .producto");
                return;
            }

            const nombre = prod.dataset.nombre;
            const precio = parseFloat(prod.dataset.precio);
            const imagen = prod.querySelector("img")?.src;

            agregarAlCarrito(nombre, precio, imagen);
        });
    });

    // =========================
    // INIT
    // =========================
    mostrarCarrito();

    // =========================
    // 🔥 EXPONER FUNCIONES AL HTML (CRÍTICO)
    // =========================
    window.eliminarProducto = eliminarProducto;
    window.vaciarCarrito = vaciarCarrito;
    window.generarBoleta = generarBoleta;

});