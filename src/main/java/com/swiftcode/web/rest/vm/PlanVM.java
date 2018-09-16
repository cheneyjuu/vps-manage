package com.swiftcode.web.rest.vm;

import lombok.Data;

/**
 * @author chen
 */
@Data
public class PlanVM {
    private Long id;
    private String name;
    private Long price;
    private String trafficLimit;
    private String speedLimit;
    private Integer ipLimit;
    private Integer validityPeriod;
}
