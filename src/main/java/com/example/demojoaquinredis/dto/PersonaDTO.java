package com.example.demojoaquinredis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PersonaDTO implements Serializable {

    private String name;
    private String lastName;
    private String email;
    private Integer age;
    private Integer documentIdentityNumber;

}
