package sevensenders.challenge.comic.service;

import sevensenders.challenge.comic.entity.Comic;

import java.util.List;

public interface ComicService {

    List<Comic> getTopTwentyComics();
}
