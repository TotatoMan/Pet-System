package com.bcb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    private String Kind;
    private String Templow;
    private String Temphigh;
    private String Humidlow;
    private String Humidhigh;
    private String Intro;
    private String Food;
}
