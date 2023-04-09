package at.ac.tuwien.qs.movierental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbResult {

    @JsonProperty("results")
    private List<TMDbMovie> movies;

    @JsonProperty("total_results")
    private Integer totalResults;

}
