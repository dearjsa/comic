package sevensenders.challenge.comic.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sevensenders.challenge.comic.entity.Comic;
import sevensenders.challenge.comic.entity.ComicApiException;
import sevensenders.challenge.comic.entity.WebComic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WebComicService {

    private static final String CURRENT_COMIC_URL = "https://xkcd.com/info.0.json";
    private static final Integer NUMBER_OF_LAST_COMICS = 11;

    public List<Comic> getTenLatestWebComics() {
        List<WebComic> allLatestComics = new ArrayList<>();
        WebComic currentWebComic = getCurrentComic();
        Integer numberOfCurrentComic = currentWebComic.getNumberOfCurrentComic();

        for (int i = 1; i < NUMBER_OF_LAST_COMICS; i++) {
            WebComic webComic = getWebComic(createNewUrl(numberOfCurrentComic, i));
            allLatestComics.add(webComic);
        }

        return allLatestComics.stream()
                .map(WebComic::toComic)
                .collect(Collectors.toList());
    }

    private WebComic getWebComic(String comicUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String result = restTemplate.getForObject(comicUrl, String.class);

        try {
            if (result == null) {
                throw new ComicApiException("Request to web comic REST API failed: Null response!");
            }

            log.info("Successful execution of REST API. [url={}]" + comicUrl);
            WebComic webComic = mapper.readValue(result, WebComic.class);
            webComic.setWebUrl(comicUrl);
            return webComic;

        } catch (JsonProcessingException e) {
            throw new ComicApiException("Request to web comic REST API failed: " + e.getMessage());
        }
    }

    private WebComic getCurrentComic() {
        return getWebComic(CURRENT_COMIC_URL);
    }

    private String createNewUrl(Integer numberOfCurrentComic, Integer i) {
        return "https://xkcd.com/" + (numberOfCurrentComic - i) + "/info.0.json";
    }

}
