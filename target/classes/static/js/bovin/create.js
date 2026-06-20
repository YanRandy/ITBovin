// Recharge la page en GET avec la race choisie pour que le contrôleur
// renvoie la liste des lots correspondant à cette race
// (LotService.findAllByRace).
function onRaceChange(idRace) {
    if (idRace) {
        window.location.href = "/bovin/create?idRace=" + idRace;
    } else {
        window.location.href = "/bovin/create";
    }
}