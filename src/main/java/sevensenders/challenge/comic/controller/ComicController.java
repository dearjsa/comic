package sevensenders.challenge.comic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sevensenders.challenge.comic.entity.Comic;
import sevensenders.challenge.comic.service.ComicService;

import java.util.List;

@RestController
@RequestMapping("/comic")
@RequiredArgsConstructor
public class ComicController {

    final ComicService comicService;

    @GetMapping("/top-20")
    public List<Comic> getRssFeed() {
        return comicService.getTopTwentyComics();
    }

}
