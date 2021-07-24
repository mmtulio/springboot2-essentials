package academy.devdojo.springboot.integration;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.repository.AnimeRepository;
import academy.devdojo.springboot.requests.AnimePostRequestBody;
import academy.devdojo.springboot.util.AnimeCreator;
import academy.devdojo.springboot.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("List retorna lista de animes dentro de objeto de página quando bem sucedido")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessfull() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange(
                "/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll retorna lista de animes quando bem sucedido")
    void list_ReturnsListOfAnime_WhenSuccessfull() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animeList = testRestTemplate.exchange(
                "/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById retorna anime quando bem sucedido")
    void findById_ReturnsAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName retorna a lista de anime quando bem sucedido")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animeList = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName retorna uma lista vazia de anime quando anime não é achado ")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){

        List<Anime> animeList = testRestTemplate.exchange(
                "/animes/find?name=nome",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save retorna anime quando bem sucedido")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace atualiza anime quando bem sucedido")
    void replace_UpdatesAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes",
                HttpMethod.PUT,new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete remove o anime quando bem sucedido")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}",
                HttpMethod.DELETE,null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
