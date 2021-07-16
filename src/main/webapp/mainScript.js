let arrayOfShips = [];
let state = "NOT_STARTED";
let arrayOfCoords = [];

function Coordinate(x, y) {
    this.x = x;
    this.y = y;
}

Coordinate.prototype.toString = function () {
    return `"x":${this.x},"y":${this.y}`;
}

function removeHandlers() {

}

function deployShips(numberOfDecks, numberOfShips) {
    if (numberOfDecks === 0) {
        removeHandlers();
    }
    let countOfDeployedShips = 0;
    let countOfDeployedDecks = 0;
    arrayOfCoords = [];
    $("#event").text("Please, place your " + countOfDeployedShips + 1 + " " + numberOfDecks + "-deck ship");
    $("#player_field td[data-x]").on("click", function () {
        let x = $(this).data('x');
        let y = $(this).data('y');
        let tileSelector = "#player_field td[data-x=" + x + "][data-y=" + y + "]";

        if ($(tileSelector).text() === "*") {
            return;
        }

        $(tileSelector).text("*");
        arrayOfCoords.push(new Coordinate($(this).data('x'), $(this).data('y')));
        countOfDeployedDecks++;
        if (countOfDeployedDecks === numberOfDecks) {
            countOfDeployedDecks = 0;
            arrayOfShips.push(arrayOfCoords);
            arrayOfCoords = [];
            countOfDeployedShips++;
            if (arrayOfShips.length === 10) {
                sendShipsToServer();
            }
            if (countOfDeployedShips === numberOfShips) {
                countOfDeployedShips = 0;
                numberOfShips += 1;
                numberOfDecks -= 1;
            }
        }

    });

}

function sendShipsToServer() {
    alert("Sending ships to server for validation!");
}

function startPlacement() {
    alert("Place your ships!");
    state = "PLACING";
    $("#event").text("Please, place your " + (1) + " " + 1 + "-deck ship");
    $("#cancel_placement button").on("click", function () {
        // Finish later,remove event handlers from tiles
        arrayOfShips = [];
        arrayOfCoords = [];
        state = "NOT_STARTED";
    })
    $("#confirm_placement button").on("click", function () {
        console.log("confirm")
        if (arrayOfShips.length === 10) {
            sendShipsToServer();
        } else alert("You're not done placing your ships!");
    })
    deployShips(4, 1);

}