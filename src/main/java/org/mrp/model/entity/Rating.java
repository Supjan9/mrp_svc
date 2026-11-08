package org.mrp.model.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Rating {
    private Long id;
    private Long userId;
    private Long mediaId;
    private Integer starRating;
    private String comment;
    private Date timeStamp;

}
