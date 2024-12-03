package si.skavtko.v1.api;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.ClanSkupina;
import si.skavtko.entitete.Skupina;
import si.skavtko.zrna.SkupinaZrno;

@Path("/skupine")
@Tag(name = "Upravljanje s Skupinami",
    description = "CRUD skupini, dolocis jim podatke, pa se clane dodajas v skupino")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SkupinaResource {

    @Inject
    SkupinaZrno skupinaZrno;

    @GET
    @Operation(summary = "Poisce skupino po id-ju",
        description = "Imas id skupine, poisces skupino")
    @Path("/{id}")
    public Response getResourceById(
        @Parameter(description = "Id skupine, ki jo isces", example = "6")
        @PathParam("id") Long id){
        Skupina skupina = skupinaZrno.getSkupina(id);
        if(skupina == null)return Response.status(Status.NOT_FOUND).build();
        
        return Response.ok(skupina).build();
    }

    @GET
    @Operation(summary = "Pridobivanje calanov skupine",
        description = "Imas id skupine, poisces njene clane")
    @Path("/{id}/clani")
    public Response getClaniSkupine (
        @Parameter(description = "Id skupine, katere clane isces", example = "6")
        @PathParam("id") Long id){
        List<Clan> clani = skupinaZrno.getClaniPoSkupini(id);

        
        if(clani.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(clani).build();
    }

    @GET
    @Operation(summary = "Pridobi skupine, v katerih je clan",
        description = "Imas id clana, poisces skupine, v katere je bil vpleten")
    public Response getId(
        @Parameter(description = "Id clana, za katerga ces znat v katerih skupinah je", example = "Peter")
        @QueryParam("clanId") Long clanId){
        List<Skupina> skupine = skupinaZrno.getSkupinePoClanu(clanId);
        
        if(skupine.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(skupine).build();
    }



    //Clane se doda posebej s PUT metodo, lahko s eposlje listo
    @POST
    @Operation(summary = "Ustvari novo skupino",
        description = "Ustvarjena skupina je brez clanov")
    public Response dodajSkupino(
        @Parameter(description = "Podatki o skupini, ki jo ustvaris")
        Skupina data){
        Skupina novaSkupina = skupinaZrno.novaSkupina(data);
        
        return Response.ok(novaSkupina).build();
    }

    @PUT
    @Operation(summary = "Posodobi podatke o skupini",
        description = "Posljes podatke posodobljene skupine")
    public Response posodobiSkupino(
        @Parameter(description = "Podatki o skupini, ki jo posodabljas")
        Skupina skupina){
        Skupina posodobljenaSkupina = skupinaZrno.posodobiSkupino(skupina);
        
        return Response.ok(posodobljenaSkupina).build();
    }

    @PUT
    @Operation(summary = "Doda enega clana v skupino",
        description = "Posljes id skupine, id clana, pa server nrdi svoje")
    @Path("/{id}/clani/{clanId}")
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response dodajClanaVSkupino(
        @Parameter(description = "Id skupine, v katero dodajas clana", example = "6")
        @PathParam("id") Long skupinaId,
        @Parameter(description = "Id clana, ki ga v skupino dodas", example = "3")
        @PathParam("clanId") Long clanId){
        ClanSkupina cs = skupinaZrno.dodajClana(skupinaId, clanId);
        
        return Response.ok(cs).build();
    }

    @PUT
    @Operation(summary = "Doda clane v skupino",
        description = "V skupino vstavi vse clane, ki so v seznamu")
    @Path("/{id}/clani")
    public Response dodajClaneVSkupino(
        @Parameter(description = "Id skupine v katero dodas clane", example = "5")
        @PathParam("id") Long skupinaId, 
        @Parameter(description = "Seznam id-jev clanov, ki jih zelis dodati v skupino", example = "[1, 2, 3]")
        List<Long> clani){
        Set<ClanSkupina> cs = skupinaZrno.dodajClane(skupinaId, clani);
        
        return Response.ok(cs).build();
    }

    @DELETE
    @Operation(summary = "Zbrise skupino",
        description = "Si se navelical ene skupine? Zbrisi jo in bos imel vec fraj cajta.")
    @Path("/{id}")
    public Response deleteResource(
        @Parameter(description = "Id skupine, katere se znebis", example = "7")
        @PathParam("id") Long id){
        skupinaZrno.deleteSkupino(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}