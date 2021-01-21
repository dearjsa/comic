package sevensenders.challenge.comic.client;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import sevensenders.challenge.comic.entity.Comic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RssFeedComicService {

    private static final String RSS_FEED_URL = "http://feeds.feedburner.com/PoorlyDrawnLines";

    public List<Comic> getTenLastRssFeed() {

        return readRssFeedEntries().stream()
                .map(entry -> new Comic(extractImageUrl(entry), entry.getTitle(), entry.getLink(), entry.getPublishedDate()))
                .collect(Collectors.toList());
    }

    private List<SyndEntry> readRssFeedEntries() {
        try {
            XmlReader reader = new XmlReader(new URL(RSS_FEED_URL));
            SyndFeed feed = new SyndFeedInput().build(reader);
            return feed.getEntries();
        } catch (IOException | FeedException e) {
            throw new RuntimeException();
        }
    }

    private String extractImageUrl(SyndEntry syndEntry) {
        Document document = Jsoup.parse(syndEntry.getContents().toString());
        Element figure = document.select("figure").first();
        Element imageUrlContainElement = figure.select("a").first();
        if (imageUrlContainElement != null) {
            return imageUrlContainElement.attr("href");
        }

        return figure.selectFirst("img").attr("src");
    }

}
