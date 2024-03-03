package org.example.carcatalog.model;

import lombok.*;

@Data
@Builder
public class Car{
    private String id;
    private String name;
    private String popular;
    private String country;
}
