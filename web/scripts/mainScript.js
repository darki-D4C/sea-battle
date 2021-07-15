let arrayOfShips = [];
let state = "NOT_STARTED";

function deployShips(numberOfDecks, numberOfShips) {

}

function sendShipsToServer() {

}

function startPlacement() {
    alert("Place your ships!");
    state = "PLACING";
    $("#cancel_placement button").click(function () {
        arrayOfShips = [];
        state = "NOT_STARTED"
    })
    deployShips(4, 1);
    deployShips(3, 2);
    deployShips(2, 3);
    deployShips(1, 4);
    sendShipsToServer(arrayOfShips);
}