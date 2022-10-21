package ru.pfr.overpayments.model.ros.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "VIDVPL", schema = "VPL")
public class VidVplRos {

    @Id
    @Column(name = "KOD")
    private Long kod;

    @Column(name = "NAME")
    private String name;

}
