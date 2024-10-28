package si.skavtko.v1.api;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clani")
@ApplicationScoped
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
public class ClaniResource {

    @GET
    public Response getResources(){
        Map<String, String> json = new HashMap<>();
        json.put("1", "Peter Kleped");
        json.put("2", "Peter PAn");
        json.put("3", "Peter Rovtar");
        json.put("4", "Peter Savron");

        return Response.ok(json).build();
    }

}
