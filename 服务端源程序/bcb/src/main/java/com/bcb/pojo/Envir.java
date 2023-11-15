package com.bcb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envir {
    Integer Count;
    Date Time;
    private int CO2;
    private int CH2O;
    private int TVOC;
    private int PM25;
    private int PM10;
    private float Temperature;
    private float Humidity;
}
