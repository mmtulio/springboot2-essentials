package academy.devdojo.springboot.client;

import academy.devdojo.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/3", Anime.class);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 3);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(animes);

        ResponseEntity<List<Anime>> exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes/all",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        log.info(exchange.getBody());


//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);

        Anime jujutsu = Anime.builder().name("jujutsu").build();
        ResponseEntity<Anime> jujutsuSaved = new RestTemplate().exchange(
                "http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(jujutsu, createJsonHeaders()),
                Anime.class
        );
        log.info("Saved animed {}", jujutsuSaved);

        Anime animeToBeUpdated = jujutsuSaved.getBody();
        animeToBeUpdated.setName("jujutsu kaizen");
        ResponseEntity<Void> jujutsuUpdated = new RestTemplate().exchange(
                "http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeaders()),
                Void.class
        );
        log.info("Saved animed {}", jujutsuUpdated);

        // DELETE
//        ResponseEntity<Void> animeDeleted = new RestTemplate().exchange(
//                "http://localhost:8080/animes/{id}",
//                HttpMethod.DELETE,
//                null,
//                Void.class,
//                animeToBeUpdated.getId()
//        );
//        log.info("Deleted animed {}", animeDeleted);
    }

    private static HttpHeaders createJsonHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
