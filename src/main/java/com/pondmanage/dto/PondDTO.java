package com.pondmanage.dto;

import com.pondmanage.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PondDTO {
    private Long id;

    private String name;

    private Boolean pondType;

    private Float area;

    private Boolean status;

    private Float waterHeight;

    private Date createdTime;

    private String emailManager;

    private Boolean isDelete = false;

    List<EnvironmentHistory> environmentHistoryList = new ArrayList<>();

    List<OtherHistory> otherHistoryList = new ArrayList<>();

    Shrimp shrimp;

    Diet diet;

    private Long zoneId;
}
