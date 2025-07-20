const addForm = document.getElementById('add-form');
const penerimaTable = document.getElementById('penerima-table').getElementsByTagName('tbody')[0];

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
                <button onclick="updateStatus(${p.id}, 'diterima')">Terima</button>
                <button onclick="updateStatus(${p.id}, 'ditolak')">Tolak</button>
                <button onclick="deletePenerima(${p.id})">Hapus</button>
            </td>
        `;
    });
};

addForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const nama = document.getElementById('nama').value;
    const alamat = document.getElementById('alamat').value;
    const penghasilan = document.getElementById('penghasilan').value;

    await fetch('/api/penerima', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nama, alamat, penghasilan })
    });

    addForm.reset();
    fetchData();
});

const updateStatus = async (id, status) => {
    const response = await fetch(`/api/penerima/${id}`);
    const penerima = await response.json();

    await fetch(`/api/penerima/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...penerima[0], status })
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
