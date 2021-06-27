package academy.devdojo.springboot.controller;

import academy.devdojo.springboot.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("anime")
public class AnimeController {

    @GetMapping(path = "list")
    public List<Anime> List(){
        return List.of(new Anime("dbz"), new Anime("opm"));

    }
}
