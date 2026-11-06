package org.mrp.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mrp.model.Genre;
import org.mrp.model.MediaType;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class Media {
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private String description;
    private MediaType mediaType;
    private Integer releaseYear;
    private Set<Genre> genres;
    private Integer ageRestriction;
    @Setter(AccessLevel.NONE)
    private Date createdAt;

}
