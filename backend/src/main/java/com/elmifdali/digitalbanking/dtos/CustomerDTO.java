package com.elmifdali.digitalbanking.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class CustomerDTO {
    private Long id ;
    private String name ;
   private String email  ;
}
