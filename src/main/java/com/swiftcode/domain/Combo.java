package com.swiftcode.domain;

import com.swiftcode.config.Constants;
import com.swiftcode.domain.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author chen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sc_combo")
public class Combo extends AbstractEntity {
    public static final String MEGABYTE = "M";
    public static final String GIGABYTE = "G";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "combo name cannot be null")
    @Size(min = 4, max = 50, message = "combo name must be between 4 and 50 characters")
    @Column(length = 50, unique = true, nullable = false)
    private String name;
    @Min(value = 1, message = "price should not be less than 1")
    private Long price;
    /**
     * 流量限制
     */
    @NotNull(message = "bandwidth quota limit cannot be null")
    private Long quotaLimit;
    /**
     * 限速
     */
    @NotNull(message = "speed limit cannot be null")
    private Long speedLimit;
    /**
     * 限制IP个数
     */
    @NotNull(message = "ip limit cannot be null")
    private Integer ipLimit;
    /**
     * 有效期
     */
    @NotNull(message = "validity period cannot be null")
    private Integer validityPeriod;

    @SuppressWarnings("unused")
    private Combo() {
    }

    public Combo(@NotNull(message = "combo name cannot be null") @Size(min = 4, max = 50, message = "combo name must be between 4 and 50 characters") String name,
                 @Min(value = 1, message = "price should not be less than 1") Long price,
                 String quotaLimit, String speedLimit, Integer ipLimit, Integer validityPeriod) {
        this.name = name;
        this.price = price;
        this.setQuotaLimit(quotaLimit);
        this.setSpeedLimit(speedLimit);
        this.ipLimit = ipLimit;
        this.validityPeriod = validityPeriod;
    }

    private void setQuotaLimit(@Pattern(regexp = Constants.BANDWIDTH_REGEX, message = "quota must end with 'M' or 'G' character") String quotaStr) {
        int quota = Integer.valueOf(quotaStr.substring(0, quotaStr.length() - 1));
        if (quotaStr.endsWith(MEGABYTE)) {
            this.quotaLimit = (long) quota * 1024 * 1024;
        }
        if (quotaStr.endsWith(GIGABYTE)) {
            this.quotaLimit = (long) quota * 1024 * 1024 * 1024;
        }
    }

    private void setSpeedLimit(String speedStr) {
        int speed = Integer.valueOf(speedStr.substring(0, speedStr.length() - 1));
        if (speedStr.endsWith(MEGABYTE)) {
            this.speedLimit = (long) speed * 1024 * 1024;
        }
        if (speedStr.endsWith(GIGABYTE)) {
            this.speedLimit = (long) speed * 1024 * 1024 * 1024;
        }
    }
}
