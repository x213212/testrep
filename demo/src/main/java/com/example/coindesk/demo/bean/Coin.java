package com.example.coindesk.demo.bean;

import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "coin")
public class Coin implements Serializable{
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    @Id
    private String pcode;
    private String psymbol;
    private Double prate;
    private String pdescription;
    private Double prate_float;
  
}
