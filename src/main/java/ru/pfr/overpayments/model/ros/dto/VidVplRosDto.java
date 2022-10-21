package ru.pfr.overpayments.model.ros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VidVplRosDto {

    private Long kod;

    private String name;

}
