const addForm = document.getElementById('add-form');
const penerimaTable = document.getElementById('penerima-table').getElementsByTagName('tbody')[0];
let editingId = null;

const fetchData = async () => {
    const response = await fetch('/api/penerima');
    const data = await response.json();
    penerimaTable.innerHTML = '';
    data.forEach(p => {
        const row = penerimaTable.insertRow();
        row.innerHTML = `
            <td>${p.nama}</td>
            <td>${p.alamat}</td>
            <td>${p.penghasilan}</td>
            <td>${p.status}</td>
            <td>
                <button class="btn btn-warning" onclick="editPenerima(${p.id}, '${p.nama}', '${p.alamat}', ${p.penghasilan})">Edit</button>
                <button class="btn btn-secondary" onclick="updateStatus(${p.id}, 'diterima')">Terima</button>
                <button class="btn btn-secondary" onclick="updateStatus(${p.id}, 'ditolak')">Tolak</button>
                <button class="btn btn-danger" onclick="deletePenerima(${p.id})">Hapus</button>
            </td>
        `;
    });
};

addForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const nama = document.getElementById('nama').value;
    const alamat = document.getElementById('alamat').value;
    const penghasilan = document.getElementById('penghasilan').value;

    if (editingId) {
        await fetch(`/api/penerima/${editingId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nama, alamat, penghasilan })
        });
        editingId = null;
        addForm.getElementsByTagName('button')[0].innerText = 'Tambah Penerima';
    } else {
        await fetch('/api/penerima', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nama, alamat, penghasilan })
        });
    }

    addForm.reset();
    fetchData();
});

const editPenerima = (id, nama, alamat, penghasilan) => {
    editingId = id;
    document.getElementById('nama').value = nama;
    document.getElementById('alamat').value = alamat;
    document.getElementById('penghasilan').value = penghasilan;
    addForm.getElementsByTagName('button')[0].innerText = 'Perbarui Penerima';
};

const updateStatus = async (id, status) => {
    await fetch(`/api/penerima/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status })
    });

    fetchData();
};

const deletePenerima = async (id) => {
    await fetch(`/api/penerima/${id}`, {
        method: 'DELETE'
    });

    fetchData();
};

fetchData();