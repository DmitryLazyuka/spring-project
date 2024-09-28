package org.example.springproject.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.springproject.model.Status;

@Data
public class StatusUpdateRequestDto {
    @NotNull
    private Status status;
}
