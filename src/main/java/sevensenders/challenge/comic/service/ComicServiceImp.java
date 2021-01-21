package sevensenders.challenge.comic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sevensenders.challenge.comic.client.RssFeedComicService;
import sevensenders.challenge.comic.client.WebComicService;
import sevensenders.challenge.comic.entity.Comic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComicServiceImp implements ComicService {

    private final RssFeedComicService rssFeedComicService;
    private final WebComicService webComicService;

    @Override
    public List<Comic> getTopTwentyComics() {
        List<Comic> allComics = webComicService.getTenLatestWebComics();
        List<Comic> rssFeedComics = rssFeedComicService.getTenLastRssFeed();
        allComics.addAll(rssFeedComics);
        return allComics.stream()
                .sorted()
                .limit(20)
                .collect(Collectors.toList());

    }
}
