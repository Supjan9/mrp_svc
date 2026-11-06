package org.mrp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Integer id;
    private String username;
    private String email;
    private String favoriteGenre;
    private Integer totalRatings;
    private Double averageScore;


}
