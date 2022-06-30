package com.pondmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTO {
    private Long id;
    private String name;
    private float area;
    private Date createdTime;
    private String address;
}
