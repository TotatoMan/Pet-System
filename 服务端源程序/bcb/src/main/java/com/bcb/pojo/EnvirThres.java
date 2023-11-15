package com.bcb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvirThres {
    private Integer id;
    private String tempLow;
    private String tempHigh;
    private String humidLow;
    private String humidHigh;
}
