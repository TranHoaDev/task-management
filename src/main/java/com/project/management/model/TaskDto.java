package com.project.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto implements Serializable {

    private Long task_id;

    @NotEmpty
    @Min(value = 10)
    private String title;

    private String description;

    private Long taskStatus_id;

    private boolean isEdit = false;


}
