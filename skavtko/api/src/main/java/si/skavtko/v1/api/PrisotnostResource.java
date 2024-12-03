package si.skavtko.v1.api;

import java.util.List;

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
import si.skavtko.entitete.Prisotnost;
import si.skavtko.zrna.PrisotnostZrno;

@Path("/prisotnosti")
@Tag(name = "Upravljanje s prisotnostimi",
    description = "CRUD prisotnosti na srecanju, oznais kdo je bil, ni bil, pa se lusne opombe napises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrisotnostResource {

    @Inject
    PrisotnostZrno prisotnostZrno;

    @GET
    @Operation(summary = "Iskanje prisotnosti",
        description = "Lahko se isce na podlagi srecanja ali na podlagi clana in skupine")
    public Response dobiPrisotnosti(
        @Parameter(description = "Id skupine rabi se v kombinaciji s clanom", example = "5")
        @QueryParam("skupina") Long skupinaId,
        @Parameter(description = "Id clana za katerega isces prisotnosti", example = "4")
        @QueryParam("clan") Long clanId,
        @Parameter(description = "Id srecanja za katerga isces prisotnosti", example = "13")
        @QueryParam("srecanje") Long srecanjeId
    ){
        List<Prisotnost> res = null;
        if(skupinaId != null){
            res = prisotnostZrno.isciPoSrecanju(srecanjeId);
        }else{
            res = prisotnostZrno.isciPoClanuInSkupini(clanId, skupinaId);
        }
        return Response.ok(res).build();
    }

    @POST
    @Operation(summary = "Dodajanje prisotnosti",
        description = "Na podlagi srecanja, ustvari prisotnosti za vse clane skupine, ki se sreca, default vrednost bo prisoten")
    @Path("/srecanja/{id}")
    public Response dodajPrisotnosti(
        @Parameter(description = "Id skupine za katero postas", example = "13")
        @PathParam("id") Long skupinaId
    ){
        List<Prisotnost> res = prisotnostZrno.dodajPrisotnosti(skupinaId);
        return Response.ok(res).build();
    }

    @PUT
    @Operation(summary = "Posodabljanje prisotnosti",
        description = "Posodobi vse prisotnosti  v seznamu")
    public Response posodobi(
        @Parameter(description = "Seznam prisotnosti, ki jih zelis posodobiti")
        List<Prisotnost> prisotnosti){
        List<Prisotnost> res = prisotnostZrno.posodobiPrisotnosti(prisotnosti);
        return Response.ok(res).build();
    }

    @DELETE
    @Operation(summary = "Posodabljanje prisotnosti",
        description = "Zbrises tiste prisotnosti, ki te ne vec zanimajo")
    public Response zbrisi(
        @Parameter(description = "Seznam prisotnosti, ki jih zbrises", example = "[15, 16]")
        List<Long> prisotnosti){
        prisotnostZrno.zbrisiPrisotnosti(prisotnosti);
        return Response.status(Status.NO_CONTENT).build();
    }
}
