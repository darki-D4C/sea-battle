package org.krista.seabattle;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;

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
        return Response.status(200).entity("Game started,place your ships!").build();
    }

    @GET
    @Consumes("application/json")
    @Path("/attack")
    public Response attackServerField() { // not finished
        JSONObject attack = this.serverField.receivePlayerAttack();
        if (attack.get("state") == "hit") {
            return Response.ok(attack).build(); // to be finished : modify attack.json to contain message about "Your turn again"
        } else {
            return Response.ok(attack).build();
        }
    }


    @GET
    @Consumes("application/json")
    @Path("/field")
    public Response sendPlayerField(JSONObject field) {
        if (this.playerField.checkPlayerShips()) {
            this.playerField.placePlayerShips();
            return Response.ok("Ships placed, awaiting player attack").build();
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
