package com.application.contactmanagementsystem.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String FullName;

    @NotNull
    private String contactNo;

    private String emailId;

    @OneToOne
    private Address address;
}
