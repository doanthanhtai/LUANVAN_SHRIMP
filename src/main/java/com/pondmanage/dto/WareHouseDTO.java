package com.pondmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO {
    private Long id;
    private String name;
    private Long zoneId;
}
