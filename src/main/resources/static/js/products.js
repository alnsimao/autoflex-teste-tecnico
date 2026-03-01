const API_URL = "http://localhost:8080/products";

async function loadProducts() {
    try {
        const response = await fetch(`${API_URL}/list`);
        if (!response.ok) throw new Error("Erro ao buscar dados");

        const products = await response.json();
        const tbody = document.querySelector("#productTable tbody");
        tbody.innerHTML = "";

        products.forEach(p => {
            const row = `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>R$ ${parseFloat(p.price).toFixed(2)}</td>
                </tr>`;
            tbody.innerHTML += row;
        });
    } catch (e) {
        console.error("Erro ao carregar produtos:", e);
    }
}

async function saveProduct() {
    const nameInput = document.getElementById("pName");
    const priceInput = document.getElementById("pPrice");

    if(!nameInput.value || !priceInput.value) {
        alert("Preencha todos os campos!");
        return;
    }

    const payload = {
        name: nameInput.value,
        price: parseFloat(priceInput.value)
    };

    try {
        const response = await fetch(`${API_URL}/`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            nameInput.value = "";
            priceInput.value = "";
            loadProducts();
        } else {
            alert("Erro ao salvar produto.");
        }
    } catch (e) {
        alert("Servidor offline ou erro de conexão.");
    }
}


document.addEventListener("DOMContentLoaded", loadProducts);