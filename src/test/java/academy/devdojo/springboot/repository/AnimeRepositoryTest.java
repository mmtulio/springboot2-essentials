package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepositoryTest;

    @Test
    @DisplayName("Save cria o anime quando bem sucedido")
    void save_PerssistsAnime_WhenSuccessful(){
        //arrange
        Anime animeToBeSaved = createAnime();

        //act
        Anime animeSaved = this.animeRepositoryTest.save(animeToBeSaved);

        //assert
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save atualiza o anime quando bem sucedido")
    void save_UpdatesAnime_WhenSuccessful(){
        //arrange
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepositoryTest.save(animeToBeSaved);

        //act
        animeSaved.setName("outro nome");
        Anime animeUpdated = this.animeRepositoryTest.save(animeSaved);

        //assert
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete remove o anime quando bem sucedido")
    void save_RemovesAnime_WhenSuccessful(){
        //arrange
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepositoryTest.save(animeToBeSaved);

        //act
        animeSaved.setName("outro nome");
        this.animeRepositoryTest.delete(animeSaved);
        Optional<Anime> animeOptional = this.animeRepositoryTest.findById(animeSaved.getId());

        //assert
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Busca pelo nome retorna lista de animes quando bem sucedido")
    void findByName_ReturnsListOfAnime_WhenSuccessfull() {
        //arrange
        Anime animeToBeSaved = createAnime();

        //act
        Anime animeSaved = this.animeRepositoryTest.save(animeToBeSaved);
        String name = animeSaved.getName();
        List<Anime> animes = this.animeRepositoryTest.findByName(name);

        //assert
        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved)
                .isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Busca pelo nome retorna lista vazia quando anime não é encontrado")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        //arrange
        //act
        List<Anime> animes = this.animeRepositoryTest.findByName("nao existe");

        //assert
        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save levanta ConstraintViolationException quando nome é vazio")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        //arrange
        Anime anime = new Anime();

        //act
        //assert

        //primeira forma
//        Assertions.assertThatThrownBy(() -> this.animeRepositoryTest.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        //segunda forma
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepositoryTest.save(anime))
                .withMessageContaining("The anime name cannot be empty");

    }

    private Anime createAnime(){
        return Anime
                .builder()
                .name("Anime Test")
                .build();
    }
}