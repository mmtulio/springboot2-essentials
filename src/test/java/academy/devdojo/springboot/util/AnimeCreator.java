package academy.devdojo.springboot.util;

import academy.devdojo.springboot.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime
                .builder()
                .name("Anime Test")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime
                .builder()
                .name("Anime Test")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime
                .builder()
                .name("Anime Test Updated")
                .id(1L)
                .build();
    }
}
