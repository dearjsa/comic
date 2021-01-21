package sevensenders.challenge.comic.sevensenders.challenge.comic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sevensenders.challenge.comic.client.RssFeedComicService;
import sevensenders.challenge.comic.client.WebComicService;
import sevensenders.challenge.comic.entity.Comic;
import sevensenders.challenge.comic.service.ComicServiceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ComicServiceTest {

    @Mock
    WebComicService webComicService;

    @Mock
    RssFeedComicService rssFeedComicService;

    @InjectMocks
    ComicServiceImp comicServiceImp;

    @Test
    void getTopTwentyComicsTest_list_must_be_sorted() {
        Comic comic1 = new Comic("comic", "comic", "comic", LocalDate.of(2001, 1, 1));
        Comic comic2 = new Comic("comic", "comic", "comic", LocalDate.of(2002, 1, 1));
        Mockito.when(webComicService.getTenLatestWebComics()).thenReturn(new ArrayList<>(Arrays.asList(comic1, comic2)));
        Mockito.when(rssFeedComicService.getTenLastRssFeed()).thenReturn(new ArrayList<>(Arrays.asList(comic1, comic2)));

        List<Comic> listOfComics = comicServiceImp.getTopTwentyComics();

        Assertions.assertThat(listOfComics).containsExactly(comic2, comic2, comic1, comic1);
    }

    @Test
    void getTopTwentyComicsTest_list_must_limit_to_twenty() {
        Mockito.when(webComicService.getTenLatestWebComics()).thenReturn(webComic_list());
        Mockito.when(rssFeedComicService.getTenLastRssFeed()).thenReturn(rssFeedComic_list());

        int sizeOfResult = comicServiceImp.getTopTwentyComics().size();

        Assertions.assertThat(sizeOfResult).isEqualTo(20);

    }

    private List<Comic> webComic_list() {
        List<Comic> webComicList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            webComicList.add(new Comic("comic" + i, "comic" + i, "comic" + i, LocalDate.now()));
        }
        return webComicList;
    }


    private List<Comic> rssFeedComic_list() {
        List<Comic> rssFeedComicList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            rssFeedComicList.add(new Comic("comic" + i, "comic" + i, "comic" + i, LocalDate.now()));
        }
        return rssFeedComicList;
    }

}
