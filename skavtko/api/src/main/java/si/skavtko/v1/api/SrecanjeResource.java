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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.entitete.Clan;
import si.skavtko.entitete.Srecanje;
import si.skavtko.zrna.ClanZrno;
import si.skavtko.zrna.SkupinaZrno;
import si.skavtko.zrna.SrecanjeZrno;

@Path("/srecanja")
@Tag(name = "Upravljanje s srecanji",
    description = "CRUD srecanj, jih isces, jih dobis, jih ustvaris, jih posodobis")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SrecanjeResource {

    @Inject
    SrecanjeZrno srecanjeZrno;

    @GET
    @Operation(summary = "Poisce srecanje na podlagi parametrov",
        description = "Si clan in isces srecanja v katerih si, al pa zelis videti srecanja, katerih se kaka skupina udelezi")
    public Response get(@QueryParam("s") Long skupinaId, @QueryParam("c") Long clanId,
    @QueryParam("od") String datumOd, @QueryParam("do") String datumDo){
        List<Srecanje> srecanja = srecanjeZrno.getSrecanjaPoClanuInSkupini(clanId, skupinaId, null, null);

        if(srecanja.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(srecanja).build();
    }

    @GET
    @Operation(summary = "Dobi srecajne po id-ju",
        description = "Das id, ti vrne srecanje")
    @Path("/{id}")
    public Response getResourceById(@PathParam("id") Long id){
        Srecanje srecanje = srecanjeZrno.getSrecanje(id);

        if(srecanje == null) return Response.status(Status.NOT_FOUND).build();
        return Response.ok(srecanje).build();
    }



    //Doda novo srecanje
    //TODO dolocit se clane, ki bodo prisotni, preko tag-ov
    @POST
    @Operation(summary = "Ustvari srecanje",
        description = "Mu poves skupino, mu poves kaksno srecanje")
    @Path("/skupina/{skupinaId}")
    public Response addResource(Srecanje data, @PathParam("skupinaId") Long skupinaId){

        Srecanje novoSrecanje = srecanjeZrno.novoSrecanje(data, skupinaId);
        
        // Gson gson = new Gson();
        return Response.ok(novoSrecanje).build();
    }

    @PUT
    @Operation(summary = "Posodobi srecanje",
        description = "Slovnicna napaka? Posodobi srecanje")
    public Response updateResource(Srecanje data){
        data = srecanjeZrno.posodobiSrecanje(data);
        return Response.ok(data).build();
    }

    @DELETE
    @Operation(summary = "Zbrise srecanje",
        description = "Se nocete vec srecat? Zbrisi srecanje in pozabi nanj")
    @Path("/{id}")
    public Response deleteResource(@PathParam("id") Long id){
        srecanjeZrno.deleteSrecanje(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
