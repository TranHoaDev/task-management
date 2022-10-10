package com.project.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]+", message = "Tên chứa kí tự cấm!")
    @Length(min = 3, max = 100, message = "username phải từ 3 đến 100 kí tự!")
    private String username;

    @Column(nullable = false, length = 64)
    private String password;

    @NotBlank(message = "Name không được trống")
    @Length(min = 3, max = 100, message = "Name phải từ 3 đến 100 kí tự!")
    private String name;
}
