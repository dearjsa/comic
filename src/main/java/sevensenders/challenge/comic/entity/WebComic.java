package sevensenders.challenge.comic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebComic {

    @JsonProperty("img")
    private String img;
    @JsonProperty
    private String title;
    @JsonProperty
    private Integer year;
    @JsonProperty
    private Integer month;
    @JsonProperty
    private Integer day;
    @JsonProperty("num")
    private Integer numberOfCurrentComic;
    private String webUrl;

    LocalDate getPublicationDate() {
        return LocalDate.of(year, month, day);
    }

    String getWebUrl() {
        String pattern = "/info.0.json";
        return webUrl.replaceAll(pattern, "");
    }

    public Comic toComic() {
        return new Comic(img, title, getWebUrl(),getPublicationDate() );
    }
}
