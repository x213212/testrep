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
@Table(name = "users")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String disclaimer;
    private String name;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date dt=new Date();
    
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated")
    private java.sql.Timestamp updated;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updatedISO")
    private java.sql.Timestamp updatedISO;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updateduk")
    private java.sql.Timestamp updateduk;
}
