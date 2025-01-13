package si.skavtko.zrna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import si.skavtko.dto.ClanSkupineDTO;
import si.skavtko.dto.SrecanjeDTO;
import si.skavtko.dto.SrecanjeVremeDTO;
import si.skavtko.entitete.Skupina;
import si.skavtko.entitete.Srecanje;

@ApplicationScoped
public class SrecanjeZrno {
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    private void init(){
    }

    @PreDestroy
    private void destroy(){
    }

    public SrecanjeVremeDTO getSrecanje(Long id) throws NoResultException{
        
        Srecanje srecanje = entityManager.find(Srecanje.class, id);
        entityManager.refresh(srecanje);
        if(srecanje == null) throw new NoResultException("Srecanja z id: " + id + "ni.\n");
        // System.out.println("Srecanje:" +srecanje.getBelezenje());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        String Url = "https://api.open-meteo.com/v1/forecast?latitude=46.0512116&longitude=14.5382698&hourly=temperature_2m,rain&timezone=Europe%2FBerlin&start_date="+srecanje.getDatumOd().format(formatter).toString()+"&end_date="+srecanje.getDatumDo().format(formatter).toString();
        String data = getWeatherInfo(Url).join();
        return new SrecanjeVremeDTO(new SrecanjeDTO(srecanje), data);
    }

    //TODO dodat pametne query parametre, ki so potrebni, al tukaj al v prisotnosti spada iskanje po stanju prisotnosti
    public List<SrecanjeDTO> getSrecanjaPoClanuInSkupini(Long idClan, Long idSkupina, LocalDateTime datumOd, LocalDateTime datumDo){
        List<SrecanjeDTO> res = entityManager.createNamedQuery("Srecanje.fromIdinDatum", SrecanjeDTO.class)
        .setParameter("skid", idSkupina)
        .setParameter("cid", idClan)
        .setParameter("od", datumOd)
        .setParameter("do", datumDo)
        .getResultList();
        
        return res;
    }

    //TODO ce je belezenje ustvari prisotnosti, za zdaj preskocim, pa kaksne clane se doda v srecanje da so obvesceni, dolocit kataera stran klice drugo, ce sploh klice
    @Transactional
    public SrecanjeDTO novoSrecanje(SrecanjeDTO srecanje, Long idSkupine){
        entityManager.getTransaction().begin();
        Srecanje novoSrecanje;
        try{
            Skupina skupina = entityManager.getReference(Skupina.class, idSkupine);
            if(skupina == null)throw new NoResultException("Skupine z id-jem "+ idSkupine + " ni!");
            novoSrecanje = new Srecanje();
            novoSrecanje.setBelezenje(false);
            novoSrecanje.setDatumOd(srecanje.getDatumOd());
            novoSrecanje.setDatumDo(srecanje.getDatumDo());
            novoSrecanje.setIme(srecanje.getIme());
            novoSrecanje.setKraj(srecanje.getKraj());
            novoSrecanje.setOpis(srecanje.getOpis());
            novoSrecanje.setSkupina(skupina);
            
            entityManager.persist(novoSrecanje);
            srecanje = new SrecanjeDTO(novoSrecanje);
        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return srecanje;
    }

    @Transactional
    public SrecanjeDTO posodobiSrecanje(SrecanjeDTO srecanje)throws NoResultException{
        entityManager.getTransaction().begin();
        
        try{

            Srecanje novoSrecanje = entityManager.find(Srecanje.class, srecanje.getId());
            if(novoSrecanje == null) throw new NoResultException("Srecanja z id-jem: " + srecanje.getId() + "ni.");
            if(srecanje.getDatumOd() != null) novoSrecanje.setDatumOd(srecanje.getDatumOd());
            if(srecanje.getDatumDo() != null)  novoSrecanje.setDatumDo(srecanje.getDatumDo());
            if(srecanje.getIme() != null)  novoSrecanje.setIme(srecanje.getIme());
            if(srecanje.getKraj() != null) novoSrecanje.setKraj(srecanje.getKraj());
            if(srecanje.getOpis() != null) novoSrecanje.setOpis(srecanje.getOpis());
            entityManager.merge(novoSrecanje);
            srecanje = new SrecanjeDTO(novoSrecanje);

        }catch(NoResultException nre){
            entityManager.getTransaction().rollback();
            throw nre;
        }catch(Exception e){
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }
        entityManager.getTransaction().commit();
        return srecanje;
    }

    @Transactional
    public void deleteSrecanje( Long srecanjeId){
        entityManager.getTransaction().begin();
        Srecanje srecanje = entityManager.find(Srecanje.class, srecanjeId);
        
        entityManager.remove(srecanje);
        entityManager.getTransaction().commit();
    }

public static CompletableFuture<String> getWeatherInfo(String targetUrl) {
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
                System.out.println("Response get vreme: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    // Parse JSON response
                    JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                    JsonArray temperatures = jsonObject.getAsJsonObject("hourly").getAsJsonArray("temperature_2m");
                    JsonArray rains = jsonObject.getAsJsonObject("hourly").getAsJsonArray("rain");

                    // Calculate average temperature and max rain
                    float totalTemperature = 0.0f;
                    float maxRain = 0.0f;

                    for (JsonElement tempElement : temperatures) {
                        totalTemperature += tempElement.getAsFloat();
                    }
                    for (JsonElement rainElement : rains) {
                        maxRain = Math.max(maxRain, rainElement.getAsFloat());
                    }

                    double averageTemperature = totalTemperature / temperatures.size();

                    String res = String.format(Locale.US,"%.2f;%.2f", averageTemperature, maxRain);

                    System.out.println(averageTemperature);
                    System.out.println(maxRain);
                    System.out.println(res);

                    return res;
                } else {
                    return "Failed to fetch weather data.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "An error occurred while fetching weather data.";
            }
        });
    }
}
