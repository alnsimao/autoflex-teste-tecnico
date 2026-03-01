const API_URL = "http://localhost:8080/products";


async function loadProducts() {
    const response = await fetch(`${API_URL}/list`);
    const data = await response.json();

    const tbody = document.querySelector("#productTable tbody");
    tbody.innerHTML = "";

    data.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>R$ ${p.price}</td>
               <td>
                   <button onclick="prepareEdit(${p.id}, '${p.name}', ${p.price})" class="btn-edit" style="width: auto; background: #4f46e5; color: white; border: none; padding: 8px 12px; border-radius: 6px; cursor: pointer; margin-right: 5px;">
                       Editar
                   </button>
                   <button onclick="deleteProduct(${p.id})" class="btn-del" style="width: auto; background: #ef4444; color: white; border: none; padding: 8px 12px; border-radius: 6px; cursor: pointer;">
                       Deletar
                   </button>
               </td>
            </tr>`;
    });
}

async function saveProduct() {
    const id = document.getElementById("prodId").value; // Campo oculto que vamos add no HTML
    const name = document.getElementById("pName").value;
    const price = document.getElementById("pPrice").value;

    const payload = { name, price: parseFloat(price) };

    if (id) {

        await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
    } else {

        await fetch(`${API_URL}/`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
    }

    clearForm();
    loadProducts();
}


async function deleteProduct(id) {
    if(confirm("Deseja excluir?")) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        loadProducts();
    }
}


function fillForm(id, name, price) {
    document.getElementById("prodId").value = id;
    document.getElementById("pName").value = name;
    document.getElementById("pPrice").value = price;
    document.querySelector("button[onclick='saveProduct()']").innerText = "Atualizar";
}


document.getElementById("btnSave").addEventListener("click", saveProduct);


function clearForm() {
    document.getElementById("prodId").value = "";
    document.getElementById("pName").value = "";
    document.getElementById("pPrice").value = "";
    document.getElementById("formTitle").innerText = "Novo Produto";
    document.getElementById("btnSave").innerText = "Salvar Produto";
    document.getElementById("btnCancel").style.display = "none";
}

document.addEventListener("DOMContentLoaded", loadProducts);