const boleta = JSON.parse(localStorage.getItem("boletaDatos"));

if (!boleta) {
    alert("No hay datos de boleta");
    window.location.href = "/";
}

// HEADER
document.getElementById("boleta-numero").textContent = "Boleta Nº: " + boleta.numero;
document.getElementById("boleta-fecha").textContent = "Fecha: " + boleta.fecha;

// TABLA
const tabla = document.getElementById("boleta-body");
let total = 0;

boleta.productos.forEach(p => {
    let subtotal = p.precio * p.cantidad;
    total += subtotal;

    tabla.innerHTML += `
        <tr>
            <td>${p.nombre}</td>
            <td>S/ ${p.precio.toFixed(2)}</td>
            <td>${p.cantidad}</td>
            <td>S/ ${subtotal.toFixed(2)}</td>
        </tr>
    `;
});

// TOTAL
document.getElementById("boleta-total").textContent = "S/ " + total.toFixed(2);

// MÉTODO DE PAGO DINÁMICO
document.getElementById("metodo").addEventListener("change", function () {
    const metodo = this.value;
    const extra = document.getElementById("extra-pago");
    extra.innerHTML = "";

    if (metodo === "Tarjeta") {
        extra.innerHTML = `
            <div class="mb-3">
                <label>Número de tarjeta:</label>
                <input type="text" class="form-control bg-dark text-white border-info">
            </div>
            <div class="mb-3">
                <label>CVV:</label>
                <input type="text" class="form-control bg-dark text-white border-info">
            </div>
        `;
    }

    if (metodo === "Yape" || metodo === "Plin") {
        extra.innerHTML = `
            <div class="mb-3">
                <label>Número de celular:</label>
                <input type="text" class="form-control bg-dark text-white border-info">
            </div>
        `;
    }
});

// CONFIRMAR PAGO
function confirmarPago() {
    const nombre = document.getElementById("cliente-nombre").value;
    const dni = document.getElementById("cliente-dni").value;
    const metodo = document.getElementById("metodo").value;

    if (!nombre || !dni || !metodo) {
        alert("Completa todos los campos");
        return;
    }

    document.getElementById("modal-pago-cuerpo").innerHTML = `
        <p><b>Cliente:</b> ${nombre}</p>
        <p><b>DNI:</b> ${dni}</p>
        <p><b>Método:</b> ${metodo}</p>
        <p><b>Total:</b> S/ ${total.toFixed(2)}</p>
        <hr class="border-info">
        <p class="text-center text-info fw-bold">¡Gracias por tu compra!</p>
    `;

    const modal = new bootstrap.Modal(document.getElementById("modal-pago-exitoso"));
    modal.show();

    // limpiar carrito después de pagar
    localStorage.removeItem("carrito");
}