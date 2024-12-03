package si.skavtko.v1.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.google.gson.Gson;

import si.skavtko.entitete.Clan;
import si.skavtko.entitete.ClanSkupina;
import si.skavtko.entitete.Skupina;
import si.skavtko.zrna.ClanZrno;
import si.skavtko.zrna.SkupinaZrno;

@Path("/skupine")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SkupinaResource {

    @Inject
    SkupinaZrno skupinaZrno;

    @GET
    @Path("/{id}")
    public Response getResourceById(@PathParam("id") Long id){
        Skupina skupina = skupinaZrno.getSkupina(id);
        if(skupina == null)return Response.status(Status.NOT_FOUND).build();
        
        // Gson gson = new Gson();
        return Response.ok(skupina).build();
    }

    @GET
    @Path("/{id}/clani")
    public Response getClaniSkupine (@PathParam("id") Long id){
        List<Clan> clani = skupinaZrno.getClaniPoSkupini(id);

        
        if(clani.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(clani).build();
    }

    @GET
    public Response getId(@QueryParam("clanId") Long clanId){
        List<Skupina> skupine = skupinaZrno.getSkupinePoClanu(clanId);
        
        if(skupine.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        //Gson gson = new Gson();
        return Response.ok(skupine).build();
    }



    //Clane se doda posebej s PUT metodo, lahko s eposlje listo
    @POST
    public Response dodajSkupino(Skupina data){
        Skupina novaSkupina = skupinaZrno.novaSkupina(data);
        
        // Gson gson = new Gson();
        return Response.ok(novaSkupina).build();
    }

    @PUT
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response posodobiSkupino(Skupina skupina){
        Skupina posodobljenaSkupina = skupinaZrno.posodobiSkupino(skupina);
        
        return Response.ok(posodobljenaSkupina).build();
    }

    @PUT
    @Path("/{id}/clani/{clanId}")
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response dodajClanaVSkupino(@PathParam("id") Long skupinaId, @PathParam("clanId") Long clanId){
        ClanSkupina cs = skupinaZrno.dodajClana(skupinaId, clanId);
        
        return Response.ok(cs).build();
    }

    @PUT
    @Path("/{id}/clani")
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response dodajClaneVSkupino(@PathParam("id") Long skupinaId, List<Long> clani){
        Set<ClanSkupina> cs = skupinaZrno.dodajClane(skupinaId, clani);
        
        return Response.ok(cs).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteResource(@PathParam("id") Long id){
        skupinaZrno.deleteSkupino(id);
        return Response.status(Status.NO_CONTENT).build();

    }
}