package sevensenders.challenge.comic.entity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class RssFeedComic {

    private String title;
    private String webUrl;
    private LocalDate publicationDate;

    public RssFeedComic(String title, String link, LocalDate pubDate) {
        this.title = title;
        this.webUrl = link;
        this.publicationDate = pubDate;
    }

    public Comic toComic() {
        return new Comic(null, webUrl, title, publicationDate);
    }

}
