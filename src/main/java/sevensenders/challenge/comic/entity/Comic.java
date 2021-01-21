package sevensenders.challenge.comic.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Data
public class Comic implements Comparable {
    @JsonProperty
    private String image;
    @JsonProperty
    private String title;
    @JsonProperty
    private String webUrl;
    @JsonProperty
    private LocalDate publicationDate;

    public Comic(String image, String title, String webUrl, Date publicationDate) {
        this.image = image;
        this.title = title;
        this.webUrl = webUrl;
        this.publicationDate = publicationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public int compareTo(Object o) {
        return ((Comic) o).publicationDate.compareTo(publicationDate);
    }
}
