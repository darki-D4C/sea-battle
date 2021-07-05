package org.krista.seabattle;

import org.json.JSONException;
import org.json.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/game")
public class BattleShipService {


    private PlayerField playerField;
    private ServerField serverField;

    @GET
    @Path("/start")
    public Response startGame() {
        this.playerField = new PlayerField();
        this.serverField = new ServerField();
        this.serverField.placeServerShips();
        return Response.status(200).entity("Game started,place your ships!").build(); // make json?
    }

    @GET
    @Consumes("application/json")
    @Path("/attack")
    public Response attackServerField() throws JSONException { // not finished
        JSONObject attack = this.serverField.receivePlayerAttack();
        if (attack.get("state") == "hit") {
            return Response.ok(attack).build(); // to be finished : modify attack.json to contain message about "Your turn again"
        } else {
            return Response.ok(attack).build();
        }
    }


    @POST
    @Consumes("application/json")
    @Path("/field")
    public Response sendPlayerField(JSONObject ships) {
        if (this.playerField.checkPlayerShips(ships)){
            this.playerField.placePlayerShips(ships);
            return Response.ok("Ships placed, awaiting player attack").build(); // make json?
        } else {
            return Response.status(406, "Invalid player field, try again").build();
        }
    }


    /*
    @GET
    @Path("server/{x}/{y}")
    public Response attackPlayerField(@PathParam("x") Integer x, @PathParam("y") Integer y) {
        //fields.attackPlayerField(x,y);
        return Response.status(200).entity("Attacked player field").build();

    }
    */

}
