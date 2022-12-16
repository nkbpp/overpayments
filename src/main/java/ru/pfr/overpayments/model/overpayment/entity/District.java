package ru.pfr.overpayments.model.overpayment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "district")
public class District implements Comparable<District> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Integer kod;

    private String name;

    @Override
    public int compareTo(District o) {
        return this.kod.compareTo(o.kod);
    }
}
