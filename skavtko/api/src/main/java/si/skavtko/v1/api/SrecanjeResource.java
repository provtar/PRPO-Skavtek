package si.skavtko.v1.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Srecanje;
import si.skavtko.zrna.ClanZrno;
import si.skavtko.zrna.SkupinaZrno;
import si.skavtko.zrna.SrecanjeZrno;

@Path("/srecanja")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SrecanjeResource {

    @Inject
    SrecanjeZrno srecanjeZrno;

    @GET
    public Response get(@QueryParam("s") Long skupinaId, @QueryParam("c") Long clanId,
    @QueryParam("od") String datumOd, @QueryParam("do") String datumDo){
        //TODO do
        List<Srecanje> srecanja = srecanjeZrno.getSrecanjaPoClanuInSkupini(clanId, skupinaId, null, null);

        if(srecanja.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(srecanja).build();
    }

    @GET
    @Path("/{id}")
    public Response getResourceById(@PathParam("id") Long id){
        Srecanje srecanje = srecanjeZrno.getSrecanje(id);

        if(srecanje == null) return Response.status(Status.NOT_FOUND).build();
        return Response.ok(srecanje).build();
    }



    //Doda novo srecanje
    //TODO dolocit se clane, ki bodo prisotni, preko tag-ov
    @POST
    @Path("/skupina/{skupinaId}")
    public Response addResource(Srecanje data, @PathParam("skupinaId") Long skupinaId){

        Srecanje novoSrecanje = srecanjeZrno.novoSrecanje(data, skupinaId);
        
        // Gson gson = new Gson();
        return Response.ok(novoSrecanje).build();
    }

    @PUT
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response updateResource(Srecanje data){
        data = srecanjeZrno.posodobiSrecanje(data);
        return Response.ok(data).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteResource(@PathParam("id") Long id){
        srecanjeZrno.deleteSrecanje(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
