package si.skavtko.v1.api;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import si.skavtko.dto.ClanDTO;
import si.skavtko.zrna.ClanZrno;

@Path("/clani")
@ApplicationScoped
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
public class ClaniResource {

    @Inject
    ClanZrno clanZrno;

    //sem bi spadala se avtentikacija, se doda kasneje


    //je smiselno dodat moùnost izpisa vseh clanov? boljse 
    // @GET
    // public Response getId(@QueryParam("ime") String ime, @QueryParam("priimek") String priimek){
    //     UUID id = clanZrno.getId(ime, priimek);

    //     if(id < 0){
    //         return Response.status(Status.NOT_FOUND).build();
    //     }

    //     ClanDTO clan = clanZrno.getClan(id);
    //     Gson gson = new Gson();
    //     return Response.ok(gson.toJson(clan)).build();
    // }

    @GET
    @Path("/{id}")
    public Response getResourceById(@PathParam("id") Long id){
        ClanDTO result = clanZrno.getClan(id);
        if(result == null)return Response.status(Status.NOT_FOUND).build();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(result)).build();
    }



    //Doda novega clana
    @POST
    public Response addResource(ClanDTO data){
        // BufferedReader br = null;
        // try {
        //     br = request.getReader();
        // } catch (Exception e) {
        //     System.out.println("Problem pri obdelavi POST clan/id");
        //     System.out.println(e.getMessage());
        //     System.out.println( e.getClass().getCanonicalName());
        // }finally{
        // }
        // if(br == null)System.out.println("Je null");

        ClanDTO ustvarjen = clanZrno.dodajClana(data);
        
        Gson gson = new Gson();
        return Response.ok(gson.toJson(ustvarjen)).build();
    }

    @PUT
    //ClanDTO data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response updateResource(ClanDTO data){
        
        data = clanZrno.posodobiClan(data);
        return Response.ok(data).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteResource(@PathParam("id") Long id){
        clanZrno.deleteClan(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
