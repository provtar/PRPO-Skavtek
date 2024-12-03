package si.skavtko.v1.api;

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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.entitete.Srecanje;
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
    public Response get(
        @Parameter(description = "Id skupine, za katero isces srecanja", example = "6")
        @QueryParam("s") Long skupinaId,
        @Parameter(description = "Id clana, za katerega isces srecanja", example = "2")
        @QueryParam("c") Long clanId,
        @Parameter(description = "Datum od katerega naprej se zacne srecanje", example = "2024-12-03T10:53:46.844Z")
        @QueryParam("od") String datumOd, 
        @Parameter(description = "Datum do katerega se srecanje konca", example = "2024-12-03T10:53:46.844Z")
        @QueryParam("do") String datumDo){
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
    public Response getResourceById(
        @Parameter(description = "Id srecanja, ki ga isces", example = "13")
        @PathParam("id") Long id){
        Srecanje srecanje = srecanjeZrno.getSrecanje(id);

        if(srecanje == null) return Response.status(Status.NOT_FOUND).build();
        return Response.ok(srecanje).build();
    }

    @POST
    @Operation(summary = "Ustvari srecanje",
        description = "Mu poves skupino, mu poves kaksno srecanje")
    @Path("/skupina/{skupinaId}")
    public Response addResource(
        @Parameter(description = "Podatki srecanja, ki ga ustvarjas")
        Srecanje data, 
        @Parameter(description = "Id skupine za katero srecanje ustvarjas", example = "5")
        @PathParam("skupinaId") Long skupinaId){

        Srecanje novoSrecanje = srecanjeZrno.novoSrecanje(data, skupinaId);
        
        return Response.ok(novoSrecanje).build();
    }

    @PUT
    @Operation(summary = "Posodobi srecanje",
        description = "Slovnicna napaka? Posodobi srecanje")
    public Response updateResource(
        @Parameter(description = "Novi podatki o srecanju")
        Srecanje data){
        data = srecanjeZrno.posodobiSrecanje(data);
        return Response.ok(data).build();
    }

    @DELETE
    @Operation(summary = "Zbrise srecanje",
        description = "Se nocete vec srecat? Zbrisi srecanje in pozabi nanj")
    @Path("/{id}")
    public Response deleteResource(
        @Parameter(description = "Id srecanja, ki je odpadlo", example = "14")
        @PathParam("id") Long id){
        srecanjeZrno.deleteSrecanje(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
