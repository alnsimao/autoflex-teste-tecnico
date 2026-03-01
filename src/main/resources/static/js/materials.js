const API_URL = "http://localhost:8080/materials";


async function getAllRawMaterials() {
    try {
        const res = await fetch(`${API_URL}/list`);
        const data = await res.json();
        const tbody = document.querySelector("table tbody");
        tbody.innerHTML = "";

        data.forEach(m => {
            tbody.innerHTML += `
                <tr>
                    <td>${m.id}</td>
                    <td>${m.name}</td>
                    <td>${m.stockQuantity}</td>
                    <td>
                        <button class="btn-edit" onclick="fillForm(${m.id}, '${m.name}', ${m.stockQuantity})">Editar</button>
                        <button class="btn-del" onclick="deleteRawMaterial(${m.id})">Deletar</button>
                    </td>
                </tr>`;
        });
    } catch (e) { console.error("Erro ao listar materiais:", e); }
}


async function addRawMaterial() {
    const id = document.getElementById("matId").value;
    const name = document.getElementById("mName").value;
    const stockQuantity = document.getElementById("mStock").value;

    if(!name || !stockQuantity) {
        alert("Preencha o nome e a quantidade!");
        return;
    }


    const payload = {
        name: name,
        stockQuantity: parseFloat(stockQuantity)
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : `${API_URL}/`;

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            clearForm();
            getAllRawMaterials();
        } else {
            alert("Erro no servidor Java ao salvar.");
        }
    } catch (e) { alert("Servidor offline!"); }
}


async function deleteRawMaterial(id) {
    if (confirm("Deseja realmente excluir este material?")) {
        try {
            const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });

            if (res.status === 204 || res.ok) {
                getAllRawMaterials();
            } else {
                alert("Erro ao deletar. Verifique se o material não está em uso.");
            }
        } catch (e) { alert("Erro de conexão."); }
    }
}


function fillForm(id, name, stock) {
    document.getElementById("matId").value = id;
    document.getElementById("mName").value = name;
    document.getElementById("mStock").value = stock;

    document.getElementById("formTitle").innerText = "Editar Material";
    document.getElementById("btnSave").innerText = "Atualizar Material";
    document.getElementById("btnCancel").style.display = "block";
}

function clearForm() {
    document.getElementById("matId").value = "";
    document.getElementById("mName").value = "";
    document.getElementById("mStock").value = "";

    document.getElementById("formTitle").innerText = "Nova Matéria-Prima";
    document.getElementById("btnSave").innerText = "Salvar Material";
    document.getElementById("btnCancel").style.display = "none";
}


document.addEventListener("DOMContentLoaded", getAllRawMaterials);