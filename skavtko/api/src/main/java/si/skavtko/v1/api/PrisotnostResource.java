package si.skavtko.v1.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiParam;
import si.skavtko.entitete.Clan;
import si.skavtko.zrna.ClanZrno;

@Path("/prisotnosti")
// @Api
@Produces(MediaType.APPLICATION_JSON)
public class PrisotnostResource {

    @Inject
    ClanZrno clanZrno;

    @GET
    // @ApiOperation(value = "test", code = 200)
    @Path("/{id}")
    public Response getPrisotnost(
        // @ApiParam(value = "Id prisotnosti, ki jo iscemo")
        @PathParam("id") Long id){
        Clan clan = clanZrno.getClan(id);
        return Response.ok(clan).build();
    }
}
