package academy.devdojo.springboot.service;

import academy.devdojo.springboot.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    //private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return List.of(new Anime(1L, "dbz"), new Anime(2L, "naruto"));
    }
}
