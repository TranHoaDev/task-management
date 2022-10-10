package com.project.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusDto implements Serializable {

    private Long taskStatus_id;
    @NotEmpty
    @Size(min = 5, max = 10)
    private String name;
    private boolean isEdit = false;
}