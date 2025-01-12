package si.skavtko.prisotnosti.zrna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import si.skavtko.prisotnosti.dto.ClanSkupineDTO;
import si.skavtko.prisotnosti.dto.PrisotnostDTO;
import si.skavtko.prisotnosti.dto.PrisotnostPutDTO;
import si.skavtko.prisotnosti.entitete.ClanMin;
import si.skavtko.prisotnosti.entitete.Prisotnost;
import si.skavtko.prisotnosti.entitete.SrecanjeMin;
import si.skavtko.prisotnosti.entitete.enums.TipPrisotnosti;

@ApplicationScoped
public class PrisotnostZrno {

    @PersistenceContext
    EntityManager entityManager;

    //se isce po srecanju, po clanu in skupini
    //se doda za vse prisotne na srecanju hkrati, za sedaj, pole se jih lahko doloci po tagu
    //filtri za odsotnosti
    //se posodablja eno prisotnost na enkrat ali celo listo in bulk, kar celo listo in bulk
    //se brise vse prisotnosti hkrati
    //kasneje dodamo anlitiko

    @PostConstruct
    private void init(){
        System.out.println("Initaialized prisZrno");
    }

    public List<PrisotnostDTO> isciPoSrecanju(Long idSrecanja){
        List<PrisotnostDTO> res = entityManager.createNamedQuery("Prisotnosti.fromSrecanje", PrisotnostDTO.class)
        .setParameter("srecanjeId", idSrecanja).getResultList();
        return res;
    }

    public List<PrisotnostDTO> isciPoClanuInSkupini(Long idClana
    // , Long idSkupine
    ){
        List<PrisotnostDTO> res = entityManager.createNamedQuery("Prisotnosti.fromClanInSkupina", PrisotnostDTO.class)
        .setParameter("clanId", idClana)
        // .setParameter("skupinaId", idSkupine)
        .getResultList();
        return res;
    }

    // TODO verzijo, ki ji samo posljes listo prisotnosti in jo persista namesto te stale
    @Transactional
    public List<PrisotnostDTO> dodajPrisotnosti(Long idSrecanja){
        entityManager.getTransaction().begin();
        ArrayList<PrisotnostDTO> prisotni = new ArrayList<>();
        try{
            // System.out.println("Zacenjam ustvarjati srecanja");
            SrecanjeMin srecanje = entityManager.find(SrecanjeMin.class, idSrecanja);
            if(srecanje == null) throw new NoResultException();
            if(srecanje.getBelezenje() == true) return isciPoSrecanju(idSrecanja);
            // System.out.println("Dobil sem srecanje");
            
            String claniSkupineUrl = "http://skupine.default.svc.cluster.local:8072/v1/skupine/"+Long.toString(srecanje.getSkupinaId())+"/clani";
            List<ClanSkupineDTO> clani = getClaniSkupine(claniSkupineUrl).join();
            //skupinaZrno.getClaniPoSkupini(srecanje.getSkupina().getId());
            // System.out.println("Dobil clanov: " + clani.size());

            for(ClanSkupineDTO c : clani){
                ClanMin cc = entityManager.find(ClanMin.class, c.getClanId());
                System.out.println("Iskal sem clana:");
                if(cc == null) continue; //TODO, razmisli, ce je tko bolj pametno, ali ce rajsi posljes napako, ce je en clan napacen
                //TODO, pazi, da se prisotnosti ne podvojijo
                //Skoraj boljse sam zaznat napako in jo poslat tistemu, ki je neumne podatke posiljal
                Prisotnost prisotnost = new Prisotnost();

                prisotnost.setPrisotnost(TipPrisotnosti.Prisoten);
                prisotnost.setClan(cc);
                prisotnost.setSrecanje(srecanje);
                entityManager.persist(prisotnost);

                prisotni.add(new PrisotnostDTO(prisotnost));
            }
            System.out.println("Ce si tukaj si skoraj na koncu");
            // srecanje.setBelezenje(true); to naredis ko se belezenje vrne
            String targetUrl = "http://srecanja.default.svc.cluster.local:8073/v1/srecanja/"+Long.toString(idSrecanja)+"/belezi";
            CompletableFuture<Boolean> asyncRequest = sendAsyncHttpRequest(targetUrl);
            //Tega tukaj ne bomo vec delali:
            Boolean belezenjeIsSet = asyncRequest.join();
            // System.out.println("Belezenje set:  " + belezenjeIsSet);
            srecanje.setBelezenje(belezenjeIsSet);
            entityManager.merge(srecanje);
            if (belezenjeIsSet) {
                // System.out.println("Na dnu funkcije");
                entityManager.getTransaction().commit();   
            }
            else{
                throw new NoResultException("connection ni uspela, je kej zafailalo");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        return prisotni;
    }

    public static CompletableFuture<Boolean> sendAsyncHttpRequest(String targetUrl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Connecting to: " + targetUrl);
                URL url = new URL(targetUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);

                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response: " + responseCode);
                // Check if response is HTTP 200 OK
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public static CompletableFuture<List<ClanSkupineDTO>> getClaniSkupine(String targetUrl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Connecting to: " + targetUrl);
                URL url = new URL(targetUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);

                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response get clani skupine: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Gson gson = new Gson();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Type listType = new TypeToken<List<ClanSkupineDTO>>(){}.getType();
                    List<ClanSkupineDTO> data = gson.fromJson(in, listType);
                    return data;
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Transactional
    public List<PrisotnostDTO> posodobiPrisotnosti(List<PrisotnostPutDTO> prisotnosti){
        ArrayList<PrisotnostDTO> res = new ArrayList<>(prisotnosti.size());
        entityManager.getTransaction().begin();
        try{
            for(PrisotnostPutDTO p : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, p.getId());
                if(prisotnost != null){ //TODO isto tukaj, sam poslt napako, ce je null?
                    prisotnost.setOpomba(p.getOpomba());
                    prisotnost.setPrisotnost(p.getPrisotnost());
                    entityManager.persist(prisotnost);
                    res.add(new PrisotnostDTO(prisotnost));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return res;
    }

    @Transactional
    public void zbrisiPrisotnosti(List<Long> prisotnosti){
        entityManager.getTransaction().begin();
        try{
            for(Long id : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, id); 
                if(prisotnost != null){
                    entityManager.remove(prisotnost);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.getTransaction().commit();
    }

    public void zbrisiPrisotnostiSrecanja(Long srecanjeId){
        entityManager.getTransaction().begin();
        List<PrisotnostDTO> prisotnosti = entityManager.createNamedQuery("Prisotnosti.fromSrecanje", PrisotnostDTO.class)
        .setParameter("srecanjeId", srecanjeId).getResultList();
        SrecanjeMin srecanje = entityManager.find(SrecanjeMin.class, srecanjeId);
        if(srecanje == null) throw new NoResultException("Srecanja z id: " + srecanjeId + "ni.\n");
        srecanje.setBelezenje(false);
        try{
            for(PrisotnostDTO id : prisotnosti){
                Prisotnost prisotnost = entityManager.find(Prisotnost.class, id.getId()); 
                if(prisotnost != null){
                    entityManager.remove(prisotnost);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return;
        }
        entityManager.getTransaction().commit();
    }
    
    public Boolean checkDBconnection(){
        try {
            // Test connection by interacting with the database
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SELECT 1").getSingleResult(); // Simple query to test the connection
            entityManager.getTransaction().commit();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while connecting to the database.");
            return false;
        }
    }
}
