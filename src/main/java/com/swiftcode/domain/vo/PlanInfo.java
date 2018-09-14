package com.swiftcode.domain.vo;

import com.swiftcode.domain.base.AbstractVo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.persistence.Embeddable;

/**
 * @author chen
 */
@Embeddable
@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PlanInfo extends AbstractVo {
    private String name;
    private Long price;
    private Long trafficLimit;
    private Long speedLimit;
    private Integer ipLimit;
    private Integer validityPeriod;

    private PlanInfo() {
        this.name = null;
        this.price = null;
        this.trafficLimit = null;
        this.speedLimit = null;
        this.ipLimit = null;
        this.validityPeriod = null;
    }
}
