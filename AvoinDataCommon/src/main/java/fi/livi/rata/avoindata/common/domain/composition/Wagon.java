package fi.livi.rata.avoindata.common.domain.composition;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wagon {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;

    @ApiModelProperty(example = "Ed")
    public String wagonType;

    @Column
    @ApiModelProperty(example = "1")
    public int location;
    @Column
    @ApiModelProperty(value = "wagon number in customer's ticket", example = "1")
    public int salesNumber;
    @Column
    @ApiModelProperty(value = "Wagon length in decimeters", example = "2640")
    public int length;
    @Column
    public Boolean playground;
    @Column
    public Boolean pet;
    @Column
    public Boolean catering;
    @Column
    public Boolean video;
    @Column
    public Boolean luggage;
    @Column
    public Boolean smoking;
    @Column
    public Boolean disabled;
    @ManyToOne
    @JoinColumn(name = "journeysection", nullable = false)
    @JsonIgnore
    public JourneySection journeysection;

    public Wagon(final Wagon wagon, final JourneySection journeysection) {
        location = wagon.location;
        salesNumber = wagon.salesNumber;
        length = wagon.length;
        playground = wagon.playground;
        pet = wagon.pet;
        catering = wagon.catering;
        video = wagon.video;
        luggage = wagon.luggage;
        smoking = wagon.smoking;
        disabled = wagon.disabled;
        this.journeysection = journeysection;
    }

    public Wagon() {
    }
}
