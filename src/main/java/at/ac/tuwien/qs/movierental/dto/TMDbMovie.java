package at.ac.tuwien.qs.movierental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbMovie {

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("popularity")
    private Float popularity;

    @JsonProperty("vote_average")
    private Float voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("release_date")
    private Date releaseDate;

}
