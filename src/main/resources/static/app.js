const API = '/api';

const form = document.getElementById('form-paciente');
const tabelaBody = document.querySelector('#tabela tbody');
const selectEsp = document.getElementById('especialidade');

async function carregarEspecialidades() {
    const res = await fetch(`${API}/especialidades`);
    const lista = await res.json();
    selectEsp.innerHTML = lista
        .map(e => `<option value="${e.id}">${e.nome}</option>`)
        .join('');
}

async function listarPacientes() {
    const res = await fetch(`${API}/pacientes`);
    const pacientes = await res.json();
    tabelaBody.innerHTML = pacientes.map(p => `
        <tr>
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${p.email}</td>
            <td>${p.telefone ?? ''}</td>
            <td>${p.especialidade?.nome ?? ''}</td>
            <td>
                <button onclick="editar(${p.id})">Editar</button>
                <button class="excluir" onclick="excluir(${p.id})">Excluir</button>
            </td>
        </tr>
    `).join('');
}

form.addEventListener('submit', async (ev) => {
    ev.preventDefault();
    const id = document.getElementById('id').value;
    const body = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        telefone: document.getElementById('telefone').value,
        dataNascimento: document.getElementById('dataNascimento').value || null,
        especialidade: { id: Number(selectEsp.value) }
    };

    const url = id ? `${API}/pacientes/${id}` : `${API}/pacientes`;
    const method = id ? 'PUT' : 'POST';

    const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });

    if (!res.ok) {
        let msg = 'Erro ao salvar.';
        try {
            const erro = await res.json();
            msg = Object.entries(erro).map(([k, v]) => `${k}: ${v}`).join('\n');
        } catch (_) { }
        alert("Erro:\n" + msg);
        return;
    }

    form.reset();
    document.getElementById('id').value = '';
    listarPacientes();
});

async function editar(id) {
    const res = await fetch(`${API}/pacientes/${id}`);
    const p = await res.json();
    document.getElementById('id').value = p.id;
    document.getElementById('nome').value = p.nome;
    document.getElementById('email').value = p.email;
    document.getElementById('telefone').value = p.telefone ?? '';
    document.getElementById('dataNascimento').value = p.dataNascimento ?? '';
    selectEsp.value = p.especialidade?.id;
}

async function excluir(id) {
    if (!confirm('Excluir este paciente?')) return;
    const res = await fetch(`${API}/pacientes/${id}`, { method: 'DELETE' });
    if (!res.ok) { alert('Erro ao excluir'); return; }
    listarPacientes();
}

document.getElementById('cancelar').addEventListener('click', () => {
    form.reset();
    document.getElementById('id').value = '';
});

// Inicialização
carregarEspecialidades();
listarPacientes();