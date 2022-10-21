package ru.pfr.overpayments.model.ros.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class VozPereRosId implements Serializable {

    private String id;

    private Integer god;

    private Integer mes;

    private Integer nn;

    private Integer recId;

    private String doc;

}
