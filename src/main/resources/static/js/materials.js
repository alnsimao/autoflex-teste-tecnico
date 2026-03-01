const API_URL = "http://localhost:8080/materials";

async function loadMaterials() {
    try {
        const response = await fetch(`${API_URL}/list`);
        const data = await response.json();
        const tbody = document.querySelector("#materialTable tbody");
        tbody.innerHTML = "";

        data.forEach(m => {
            tbody.innerHTML += `
                <tr>
                    <td>${m.id}</td>
                    <td>${m.name}</td>
                    <td>${m.stockQuantity}</td>
                    <td style="text-align: center;">
                        <button onclick="fillForm(${m.id}, '${m.name}', ${m.stockQuantity})" style="width:auto; background:var(--primary); padding: 5px 10px;">Editar</button>
                        <button onclick="deleteMaterial(${m.id})" style="width:auto; background:#dc3545; padding: 5px 10px;">Deletar</button>
                    </td>
                </tr>`;
        });
    } catch (e) { console.error("Erro ao carregar materiais", e); }
}

async function saveMaterial() {
    const id = document.getElementById("matId").value;
    const name = document.getElementById("mName").value;
    const stockQuantity = document.getElementById("mStock").value;

    const payload = { name, stockQuantity: parseFloat(stockQuantity) };
    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : `${API_URL}/`;

    const res = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        clearForm();
        loadMaterials();
    } else {
        alert("Erro ao processar matéria-prima.");
    }
}

async function deleteMaterial(id) {
    if(confirm("Excluir esta matéria-prima?")) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        loadMaterials();
    }
}

function fillForm(id, name, stock) {
    document.getElementById("matId").value = id;
    document.getElementById("mName").value = name;
    document.getElementById("mStock").value = stock;
    document.getElementById("formTitle").innerText = "Editar Matéria-Prima";
    document.getElementById("btnSave").innerText = "Atualizar Matéria-Prima";
    document.getElementById("btnCancel").style.display = "block";
}

function clearForm() {
    document.getElementById("matId").value = "";
    document.getElementById("mName").value = "";
    document.getElementById("mStock").value = "";
    document.getElementById("formTitle").innerText = "Nova Matéria-Prima";
    document.getElementById("btnSave").innerText = "Salvar Matéria-Prima";
    document.getElementById("btnCancel").style.display = "none";
}

document.addEventListener("DOMContentLoaded", loadMaterials);