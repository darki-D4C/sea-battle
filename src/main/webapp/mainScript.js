let arrayOfShips = [];
let state = "NOT_STARTED";
let arrayOfCoords = [];

function Coordinate(x, y) {
    this.x = x;
    this.y = y;
}

function Ship(shipParts) {
    this.shipParts = shipParts;
}

Coordinate.prototype.toString = function () {
    return `{"x":${this.x},"y":${this.y}}`;
}

Ship.prototype.toString = function () {
    return `{"shipParts":[${this.shipParts}]}`;
}

function removeHandlers() {
    $("#cancel_placement button").off();
    $("#confirm_placement button").off();
}

function deployShips(numberOfDecks, numberOfShips) {
    if (numberOfDecks === 0) {
        removeHandlersForPlacing();
    }
    let countOfDeployedShips = 0;
    let countOfDeployedDecks = 0;
    arrayOfCoords = [];
    $("#event").text("Please, place your " + (countOfDeployedShips + 1) + " " + numberOfDecks + "-deck ship");
    $("#player_field td[data-x][data-y]").on("click", function () {
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
            arrayOfShips.push(new Ship(arrayOfCoords));
            arrayOfCoords = [];
            countOfDeployedShips++;
            if (arrayOfShips.length === 10) {
                removeHandlersForPlacing();
            }
            if (countOfDeployedShips === numberOfShips) {
                countOfDeployedShips = 0;
                numberOfShips += 1;
                numberOfDecks -= 1;
            }
        }

    });

}

function removeHandlersForPlacing() {
    $("#player_field td[data-x]").off();
}

function startGame() {

    $.ajax({
        type: "GET",
        url: "api/game/start",
        dataType: 'json',
        success: [function (data) {
            state = data.status;
        }]
    });
}


function attackTile(x, y) {
    if (state === "player_won" || state === "server_won") {
        return;
    }
    $.ajax({
        type: "POST",
        url: "api/game/attack",
        data: JSON.stringify(new Coordinate(x, y)),
        contentType: "application/json",
        dataType: 'json',
        success: [function (result) {
            changeField(result);
        }]
    });
}

function changeField(result) {
    if (result[0] === "invalid") {
        $("#turn_block").text("Invalid coordinate, try again");
    }
    if (result[0] === "player") {
        $("#turn_block").text("Player won!");
    } else if (result[0] === "server") {
        $("#turn_block").text("Server won!");
    }

    if (result[0] === "player" && !result[1].hasOwnProperty("shipParts")) {
        let tileSelector = "#enemy_field td[data-x=" + result[1].x + "][data-y=" + result[1].y + "]";
        $(tileSelector).text("/");
    }
    if (result[0] === "player" && result[1].hasOwnProperty("shipParts")) {
        result[1].shipParts.forEach(part => {
            let tileSelector = "#enemy_field td[data-x=" + part.x + "][data-y=" + part.y + "]";
            $(tileSelector).text("X");
        })
    }

    if (result[0].hasOwnProperty("both_missed")) {
        let tileSelector = "#enemy_field td[data-x=" + result[0].both_missed.x + "][data-y=" + result[0].both_missed.y + "]";
        $(tileSelector).text("O");
    }

    if (result[0].hasOwnProperty("missed")) {
        let tileSelector = "#enemy_field td[data-x=" + result[0].missed.x + "][data-y=" + result[0].missed.y + "]";
        $(tileSelector).text("O");
        result.forEach(elem => {
            if (elem.hasOwnProperty("missed") || elem.hasOwnProperty("side")) {
                // skip "missed" and "side" property
            } else {
                if (elem.hasOwnProperty("shipParts")) {
                    elem.shipParts.forEach(part => {
                        let tileSelector = "#player_field td[data-x=" + part.x + "][data-y=" + part.y + "]";
                        $(tileSelector).text("X");
                    })
                } else {
                    let tileSelector = "#player_field td[data-x=" + elem.x + "][data-y=" + elem.y + "]";
                    $(tileSelector).text("X");
                }
            }
        })
    }

}

function startAttackPhase() {
    console.log("Attack time!")
    $("#start_placement button").off();// for some reason this line fails to remove onclick handler
    $("#enemy_field td[data-x]").on("click", function () {

        let x = $(this).data('x');
        let y = $(this).data('y');
        let tileSelector = "#enemy_field td[data-x=" + x + "][data-y=" + y + "]";

        if ($(tileSelector).text() === "X" || $(tileSelector).text() === "O") {
            return;
        }

        attackTile(x, y);

    });

}

function sendShipsToServer() {
    /*
    console.log(arrayOfShips)


    let stringShips = [];
    arrayOfShips.forEach(ship => stringShips.push(ship.toString()))
    */

    console.log(JSON.stringify(arrayOfShips))


    $.ajax({
            type: "POST",
            url: "api/game/field",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(arrayOfShips),
            success: function (result) {
                if (result.ships === "valid") {
                    $("#turn_block").text("Your turn, attack enemy tile")
                    startAttackPhase();
                } else if (result.ships === "invalid") {
                    $("#turn_block").text("Invalid ships placement, please place again");
                } else {
                    $("#turn_block").text("Already sent ships");
                }

            }
        }
    )

    alert(arrayOfShips);
}


function clearPlayerField() {
    arrayOfShips = [];
    arrayOfCoords = [];
    $("#player_field td[data-x]").each(function () {
        $(this).text("");
    })

}

function startPlacement() {
    startGame();
    clearPlayerField();
    alert("Place your ships!");
    state = "PLACING";
    $("#confirm_placement button").on("click", function () {
        console.log("confirm")
        if (arrayOfShips.length === 10) {
            removeHandlers();
            sendShipsToServer();
        } else alert("You're not done placing your ships!");
    });
    $("#cancel_placement button").on("click", function () {
        // Finish later,remove event handlers from tiles
        arrayOfShips = [];
        arrayOfCoords = [];
        state = "NOT_STARTED";
        removeHandlersForPlacing();
        removeHandlers();
        clearPlayerField();
    })

    deployShips(4, 1);

}