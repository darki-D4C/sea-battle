let arrayOfShips = [];
let state = "NOT_STARTED";
let arrayOfCoords = [];
let playerName;

let startTime;
let elapsedTime = 0;
let timerInterval;
let dateOfPlay = new Date(Date.now());
let winner;

function timeToString(time) {
    let diffInHrs = time / 3600000;
    let hh = Math.floor(diffInHrs);

    let diffInMin = (diffInHrs - hh) * 60;
    let mm = Math.floor(diffInMin);

    let diffInSec = (diffInMin - mm) * 60;
    let ss = Math.floor(diffInSec);

    let diffInMs = (diffInSec - ss) * 100;
    let ms = Math.floor(diffInMs);

    let formattedMM = mm.toString().padStart(2, "0");
    let formattedSS = ss.toString().padStart(2, "0");
    let formattedMS = ms.toString().padStart(2, "0");

    return `${formattedMM}:${formattedSS}:${formattedMS}`;
}

function startTimer() {
    startTime = Date.now() - elapsedTime;
    timerInterval = setInterval(function printTime() {
        elapsedTime = Date.now() - startTime;
        $("#timer").text(timeToString(elapsedTime));
    }, 10);
}


function sendNameAndStart() {
    playerName = $("#name").val();
    if (playerName.length > 0) {
        $("#name").val(null);
        console.log($("#name").val())
        $("#name_input").hide();
        $("#player_field .field_name").text(playerName + "'s field")
        $("#turn_block").text("Start game by placing your ships");
        $("#start_placement button").on("click", function () {
            startPlacement();
        })
    }


}

function startOver() {
    $("#name_input").show();
    removeHandlersForAttack();
    removeHandlersForPlacing();
    removeHandlers();
    clearFields();
    $("#timer").text("00:00:00");
    $("#turn_block").text("Start game by entering your name");
    $("#event").text("");
    $("#start_placement button").off();
    $("#player_field .field_name").text("Player field")


}

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
            $("#event").text("Please, place your " + (countOfDeployedShips + 1) + " " + numberOfDecks + "-deck ship");
            if (arrayOfShips.length === 10) {
                removeHandlersForPlacing();
            }
            if (countOfDeployedShips === numberOfShips) {
                countOfDeployedShips = 0;
                numberOfShips += 1;
                numberOfDecks -= 1;
                if (numberOfDecks === 0) {
                    $("#event").text("");

                } else {
                    $("#event").text("Please, place your " + (countOfDeployedShips + 1) + " " + numberOfDecks + "-deck ship");
                }
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

function removeHandlersForAttack() {
    $("#enemy_field td[data-x]").off();
}

function getDateOfPlay() {
    let date_ob = new Date(dateOfPlay);
    let date = ("0" + date_ob.getDate()).slice(-2);
    let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);
    let year = date_ob.getFullYear();
    let hours = date_ob.getHours();
    let minutes = date_ob.getMinutes();
    let seconds = date_ob.getSeconds();
    return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
}

function updateEventBarWinner(side) {
    if(side === "Server"){
        state = "server_won"
    }else{
        state = "player_won"
    }
    /*
    console.log(playerName);
    console.log(getDateOfPlay());
    console.log($("#timer").text());
    console.log(state);

     */
    $("#event").text(side + " has won!")
}

function updateEventBarCoord(coord) {
    $("#event").text(playerName + " destroyed tile at (" + matchNumWithLetter(coord.x) + ", " + (coord.y + 1) + ")");
}

function updateEventBarShip(shipParts) {
    let eventString;
    if (Object.keys(shipParts).length !== 1) {
        eventString = playerName + " destroyed ship with tiles:";
        shipParts.forEach(elem => {
            eventString += " (" + matchNumWithLetter(elem.x) + ", " + (elem.y + 1) + ")"
        })
    } else {
        eventString = playerName + " destroyed ship with tile: (" + matchNumWithLetter(shipParts[0].x) + ", " + (shipParts[0].y + 1) + ")";
    }
    $("#event").text(eventString);

}

function updateEventBarBothMissed(missedCoord) {
    let evenString = playerName + " missed at (" + matchNumWithLetter(missedCoord.x) + ", " + (missedCoord.y + 1) + "), Server missed";
    $("#event").text(evenString);
}

function updateEventBarServer(serverAttack) {
    serverAttack.splice(1, 1);
    let eventString = playerName + " missed at (" + matchNumWithLetter(serverAttack[0].missed.x) + ", " + (serverAttack[0].missed.y + 1) + ")";
    serverAttack.forEach(attack => {
        if (!attack.hasOwnProperty("missed")) {
            if (Object.keys(attack).length !== 2) {
                eventString += ",Server destroyed ship with tiles:";
                console.log(attack)
                attack.shipParts.forEach(elem => {
                    eventString += " (" + matchNumWithLetter(elem.x) + ", " + elem.y + ")"
                })
            } else {
                if (attack.hasOwnProperty("shipParts")) {
                    eventString += ",Server destroyed ship with tile: (" + matchNumWithLetter(attack.shipParts[0].x) + ", " + (attack.shipParts[0].y + 1) + ")";
                } else {
                    eventString += ",Server destroyed tile at : (" + matchNumWithLetter(attack.x) + ", " + (attack.y + 1) + ")";
                }
            }
        }
    })
    $("#event").text(eventString);
}

function matchNumWithLetter(num) {
    switch (num) {
        case 0:
            return 'A';
        case 1:
            return 'B'
        case 2:
            return 'C';
        case 3:
            return 'D'
        case 4:
            return 'E';
        case 5:
            return 'F'
        case 6:
            return 'G';
        case 7:
            return 'I'
        case 8:
            return 'J';
        case 9:
            return 'K'
    }
}

function sendInfoToDataBase() {

}

function changeField(result) {
    if (result[0].missed || result[0].both_missed) {
        $("#turn_block").text("Server turn");
    }
    if (result[0].coordinate === "invalid") {
        $("#turn_block").text("Invalid coordinate, try again");
    }
    if (result[0].winner === "player") {
        $("#turn_block").text("Player won!");
        result[1].shipParts.forEach(part => {
            let tileSelector = "#enemy_field td[data-x=" + part.x + "][data-y=" + part.y + "]";
            $(tileSelector).text("X");
        })
        updateEventBarWinner(playerName);
        clearInterval(timerInterval);
        winner = "Player";
        removeHandlersForAttack();
        sendInfoToDataBase();
        //add info to database
    } else if (result[0].winner === "server") {
        result.forEach(elem => {
            if (elem.hasOwnProperty("winner")) {
                //skip elem with property winner
            } else {
                elem.shipParts.forEach(part => {
                    let tileSelector = "#player_field td[data-x=" + part.x + "][data-y=" + part.y + "]";
                    $(tileSelector).text("X");
                })
            }
        })
        updateEventBarWinner("Server");
        winner = "Server";
        $("#turn_block").text("Server won!");
        //add info to database
    }

    if (result[0].side === "player" && !result[1].hasOwnProperty("shipParts")) {
        let tileSelector = "#enemy_field td[data-x=" + result[1].x + "][data-y=" + result[1].y + "]";
        $(tileSelector).text("/");
        updateEventBarCoord(result[1]);
        console.log("Player destroyed cord")
    }
    if (result[0].side === "player" && result[1].hasOwnProperty("shipParts")) {

        console.log("Player destroyed ship")
        result[1].shipParts.forEach(part => {
            let tileSelector = "#enemy_field td[data-x=" + part.x + "][data-y=" + part.y + "]";
            $(tileSelector).text("X");
        })

        updateEventBarShip(result[1].shipParts);
    }

    if (result[0].hasOwnProperty("both_missed")) {
        let tileSelector = "#enemy_field td[data-x=" + result[0].both_missed.x + "][data-y=" + result[0].both_missed.y + "]";
        $(tileSelector).text("O");
        updateEventBarBothMissed(result[0].both_missed);
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
                    $(tileSelector).text("/");
                }
            }
        })
        updateEventBarServer(result);
    }

    setTimeout(function () {
        $("#turn_block").text("Player's turn")
    }, 600);

}

function startAttackPhase() {
    console.log("Attack time!")
    startTimer();
    dateOfPlay = Date.now();
    $("#start_placement button").off();// for some reason this line fails to remove onclick handler
    $("#enemy_field td[data-x]").on("click", function () {

        let x = $(this).data('x');
        let y = $(this).data('y');
        let tileSelector = "#enemy_field td[data-x=" + x + "][data-y=" + y + "]";

        if ($(tileSelector).text() === "X" || $(tileSelector).text() === "O" || $(tileSelector).text() === "/") {
            return;
        }

        attackTile(x, y);

    });

}

function sendShipsToServer() {
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
}

function clearFields() {
    arrayOfShips = [];
    arrayOfCoords = [];
    $("#player_field td[data-x]").each(function () {
        $(this).text("");
    })
    $("#enemy_field td[data-x]").each(function () {
        $(this).text("");
    })

}

function startPlacement() {
    //startTimer();
    //getDate();
    startGame();
    clearFields();
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
        $("#turn_block").text("Start game by placing your ships")
        $("#event").text("");
        removeHandlersForPlacing();
        removeHandlers();
        clearFields();
    })

    deployShips(4, 1);

}

function showHelp() {
    alert("Place your ships either horizontally or vertically, " +
        "there are 4 kinds of ships , with one deck, two decks, " +
        "three decks and four decks." +
        "By clicking on fields you can either place a deck, or attack a tile." +
        "First side to destroy all opponents ships wins." +
        "Good luck!");

}