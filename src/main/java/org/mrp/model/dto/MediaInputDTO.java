package org.mrp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaInputDTO {
    private String title;
    private String description;
    private String mediaType;
    private Integer releaseYear;
    private List<String> genres;
    private Integer ageRestriction;

}
