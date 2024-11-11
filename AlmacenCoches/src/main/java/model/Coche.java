package model;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Coche implements Serializable {
    @Getter
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
}

