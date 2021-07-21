package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepositoryTest;

    @Test
    @DisplayName("Save cria o anime quando bem sucedido")
    void save_PerssistAnime_WhenSuccessful(){

        //arrange
        Anime animeToBeSaved = createAnime();

        //act
        Anime animeSaved = this.animeRepositoryTest.save(animeToBeSaved);

        //assert
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    private Anime createAnime(){
        return Anime
                .builder()
                .name("Anime Test")
                .build();
    }
}