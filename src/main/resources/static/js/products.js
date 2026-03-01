const API_URL = "http://localhost:8080/products";

// @GetMapping("/list")
async function getAllProducts() {
    const res = await fetch(`${API_URL}/list`);
    const data = await res.json();
    const tbody = document.getElementById("prodTableBody");
    tbody.innerHTML = "";
    data.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>R$ ${p.price}</td>
                <td>
                    <button class="btn-edit" onclick="fillForm(${p.id}, '${p.name}', ${p.price})">Editar</button>
                    <button class="btn-del" onclick="deleteProduct(${p.id})">Deletar</button>
                </td>
            </tr>`;
    });
}

// @PostMapping("/") ou @PutMapping("/{id}")
async function addProduct() {
    const id = document.getElementById("prodId").value;
    const name = document.getElementById("pName").value;
    const price = document.getElementById("pPrice").value;

    const payload = { name, price: parseFloat(price) };
    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : `${API_URL}/`;

    const res = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        clearForm();
        getAllProducts();
    }
}

// @DeleteMapping("/{id}")
async function deleteProduct(id) {
    if (confirm("Excluir produto?")) {
        const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (res.status === 204 || res.ok) {
            getAllProducts();
        }
    }
}

function fillForm(id, name, price) {
    document.getElementById("prodId").value = id;
    document.getElementById("pName").value = name;
    document.getElementById("pPrice").value = price;
    document.getElementById("formTitle").innerText = "Editar Produto";
    document.getElementById("btnSave").innerText = "Atualizar";
    document.getElementById("btnCancel").style.display = "block";
}

function clearForm() {
    document.getElementById("prodId").value = "";
    document.getElementById("pName").value = "";
    document.getElementById("pPrice").value = "";
    document.getElementById("formTitle").innerText = "Novo Produto";
    document.getElementById("btnSave").innerText = "Salvar Produto";
    document.getElementById("btnCancel").style.display = "none";
}

document.addEventListener("DOMContentLoaded", getAllProducts);