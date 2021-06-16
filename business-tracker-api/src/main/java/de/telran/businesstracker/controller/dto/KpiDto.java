package de.telran.businesstracker.controller.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class KpiDto {
    public String kpi;

    public KpiDto(String kpi) {
        this.kpi = kpi;
    }
}
