package org.amida.backend.request;

import lombok.Data;
import org.amida.backend.model.Status;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
}
