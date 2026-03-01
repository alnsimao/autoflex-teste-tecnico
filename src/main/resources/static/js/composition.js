const COMP_URL = "http://localhost:8080/composition";

async function initComposition() {
    const resP = await fetch("http://localhost:8080/products/list");
    const products = await resP.json();
    document.getElementById("selProduct").innerHTML = products.map(p => `<option value="${p.id}">${p.name}</option>`).join('');

    const resM = await fetch("http://localhost:8080/materials/list");
    const materials = await resM.json();
    document.getElementById("selMaterial").innerHTML = materials.map(m => `<option value="${m.id}">${m.name}</option>`).join('');

    loadProductCompositions();
    loadAvailableProduction();
}

// CORREÇÃO DO EDITAR: Preenche o formulário
function fillCompForm(id, prodId, matId, qty) {
    document.getElementById("compId").value = id;
    document.getElementById("selProduct").value = prodId;
    document.getElementById("selMaterial").value = matId;
    document.getElementById("cQty").value = qty;

    document.getElementById("compTitle").innerText = "📝 Editando Vínculo";
    document.getElementById("btnCancelComp").style.display = "block";

    document.getElementById("compTitle").scrollIntoView({ behavior: 'smooth' });
}


async function loadAvailableProduction() {
    try {
        const res = await fetch(`${COMP_URL}/available`);
        const data = await res.json();
        const div = document.getElementById("productionList");

        if (data.length === 0) {
            div.innerHTML = "<p>Nenhum produto com composição cadastrada.</p>";
            return;
        }

        div.innerHTML = data.map(item => `
            <div style="padding:15px; border-bottom:1px solid #f1f5f9; display:flex; justify-content:space-between; align-items:center;">
                <div>
                    <strong style="color:#1e293b; display:block;">${item.productName}</strong>
                    <small style="color:#64748b;">ID: ${item.productId}</small>
                </div>
                <span class="badge-prod">${item.maxQuantityPossible} un</span>
            </div>
        `).join('');
    } catch (e) { console.error("Erro RF008:", e); }
}


async function loadProductCompositions() {
    const productId = document.getElementById("selProduct").value;
    const tbody = document.getElementById("compTableBody");
    if (!productId) return;

    try {
        const res = await fetch(`${COMP_URL}/list?productId=${productId}`);
        const data = await res.json();
        tbody.innerHTML = "";

        data.forEach(c => {
            tbody.innerHTML += `
                <tr>
                    <td>${c.rawMaterialName}</td>
                    <td>${c.quantityNeeded}</td>
                    <td style="display:flex; gap:5px;">
                        <button onclick="fillCompForm(${c.id}, ${c.productId}, ${c.rawMaterialId}, ${c.quantityNeeded})"
                                style="background:#f59e0b; border:none; border-radius:4px; cursor:pointer; padding:5px;">✏️</button>
                        <button onclick="deleteComp(${c.id})"
                                style="background:#ef4444; border:none; border-radius:4px; cursor:pointer; padding:5px;">🗑️</button>
                    </td>
                </tr>`;
        });
    } catch (e) { tbody.innerHTML = "<tr><td colspan='3'>Sem matérias vinculadas.</td></tr>"; }
}

async function saveComposition() {
    const id = document.getElementById("compId").value;
    const payload = {
        productId: parseInt(document.getElementById("selProduct").value),
        rawMaterialId: parseInt(document.getElementById("selMaterial").value),
        quantityNeeded: parseFloat(document.getElementById("cQty").value)
    };


    const method = id ? 'PUT' : 'POST';
    const url = id ? `${COMP_URL}/${id}` : `${COMP_URL}/`;

    const res = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        clearCompForm();
        loadProductCompositions();
        loadAvailableProduction();
    }
}

async function deleteComp(id) {
    if (confirm("Remover este material?")) {
        await fetch(`${COMP_URL}/${id}`, { method: 'DELETE' });
        loadProductCompositions();
        loadAvailableProduction();
    }
}

function clearCompForm() {
    document.getElementById("compId").value = "";
    document.getElementById("cQty").value = "";
    document.getElementById("compTitle").innerText = "🔗 Composição (RF007)";
    document.getElementById("btnCancelComp").style.display = "none";
}

document.addEventListener("DOMContentLoaded", initComposition);