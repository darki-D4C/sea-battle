package org.krista.seabattle;



import javax.enterprise.context.SessionScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/game")
@SessionScoped
public class BattleShipService implements Serializable {


    private GameService gameService;


    @GET
    @Path("/start")
    public Response startGame() {
        this.gameService = new GameService();
        return Response.status(200).entity("{\"succsess\":\"true\"}").build(); // make json?
    }

    /*
    @GET
    @Consumes("application/json")
    @Path("/attack")
    public Response attackServerField() throws  { // not finished

        if (attack.get("state") == "hit") {
            return Response.ok(attack).build(); // to be finished : modify attack.json to contain message about "Your turn again"
        } else {
            return Response.ok(attack).build();
        }
    }
    */



    @POST
    @Consumes("application/json")
    @Path("/field")
    public Response sendPlayerField(ArrayList<BattleShip> ships) {

        if (gameService.checkPlayerShips(ships)){
            gameService.placePlayerShips(ships);
            return Response.ok("{\"ships\":\"valid\"}").build(); // make json?
        } else {
            return Response.ok("{\"ships\":\"invalid\"}").build();
        }
    }




}
