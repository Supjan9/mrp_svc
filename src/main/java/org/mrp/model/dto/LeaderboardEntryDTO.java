package org.mrp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntryDTO {
    private String userName;
    private Integer totalRatings;
}
