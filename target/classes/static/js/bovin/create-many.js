document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("bovinForm");

    form.addEventListener("submit", handleAddRow);
});

function handleAddRow(event) {
    event.preventDefault();

    const poidsInitial = document.getElementById('poidsInitial').value;
    const dateNaissance = document.getElementById('dateNaissance').value;
    const dateArrivee = document.getElementById('dateArrivee').value;
    const quantite = document.getElementById('quantite').value;

    const tableBody = document.getElementById('bovinTableBody');

    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${poidsInitial}</td>
        <td>${dateNaissance}</td>
        <td>${dateArrivee}</td>
        <td>${quantite}</td>
        <td><button type="button" onclick="deleteRow(this)">Supprimer</button></td>
    `;

    tableBody.appendChild(row);

    event.target.reset();
}
// function deleteRow(button) {
//     const row = button.parentNode.parentNode;
//     row.parentNode.removeChild(row);
// }
function deleteRow(button) {
    button.closest("tr").remove();
}
// function getAllLignes() {
//     const lignes = [];
//     const tableBody = document.getElementById('bovinTableBody');
//     const rows = tableBody.querySelectorAll('tr');

//     rows.forEach(row => {
//         const cells = row.querySelectorAll('td');
//         const ligneData = {
//             idLot: parseInt(document.getElementById('idLot').dataset.id),
//             idRace: parseInt(document.getElementById('idRace').dataset.id),
//             poidsInitial: parseFloat(cells[0].textContent),
//             poidsActuelle: parseFloat(cells[0].textContent),
//             dateNaissance: cells[1].textContent,
//             dateArrivee: cells[2].textContent,
//             quantite: parseInt(cells[3].textContent)
//         };
//         lignes.push(ligneData);
//     });
//     return lignes;
// }
function getAllLignes() {
    const rows = document.querySelectorAll("#bovinTableBody tr");

    return Array.from(rows).map(row => {
        const cells = row.querySelectorAll("td");

        return {
            idLot: document.getElementById("idLot").dataset.id,
            idRace: document.getElementById("idRace").dataset.id,
            poidsInit: cells[0].textContent,
            poidsActuelle: cells[0].textContent,
            dateInit: cells[1].textContent,
            dateArrivee: cells[2].textContent,
            quantite: cells[3].textContent
        };
    });
}
// function save() {
//     const lignes = getAllLignes();
//     console.log(JSON.stringify(lignes));

//     fetch('/bovin/lot/' + document.getElementById('idLot').textContent + '/create', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(lignes)
//     }).then(response => {
//         if (response.ok) {
//             alert('Bovins enregistrés avec succès !');
//         } else {
//             alert('Erreur lors de l\'enregistrement des bovins.');
//         }
//     }).catch(error => {
//         console.error('Erreur:', error);
//         alert('Erreur lors de l\'enregistrement des bovins.');
//     });
// }
function save() {
    fetch(`/bovin/lot/${document.getElementById('idLot').dataset.id}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(getAllLignes())
    })
    .then(r => {
        if (r.ok) alert("OK");
        else alert("Erreur");
    });
}