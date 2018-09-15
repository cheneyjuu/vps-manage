package com.swiftcode.domain;

import com.swiftcode.config.Constants;
import com.swiftcode.domain.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 套餐.
 *
 * @author chen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sc_account_plan")
public class Plan extends AbstractEntity {
    public static final String MEGABYTE = "MB";
    public static final String MEGABYTE_SHORT = "M";
    public static final String GIGABYTE = "GB";
    public static final String GIGABYTE_SHORT = "G";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "plan name cannot be null")
    @Size(min = 4, max = 50, message = "plan name must be between 4 and 50 characters")
    @Column(length = 50, unique = true, nullable = false)
    private String name;
    @Min(value = 1, message = "price should not be less than 1")
    private Long price;
    /**
     * 流量限制
     */
    @NotNull(message = "bandwidth traffic limit cannot be null")
    private Long trafficLimit;
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
    private Plan() {
    }

    public Plan(@NotNull(message = "plan name cannot be null") @Size(min = 4, max = 50, message = "plan name must be between 4 and 50 characters") String name,
                @Min(value = 1, message = "price should not be less than 1") Long price,
                String trafficLimit, String speedLimit, Integer ipLimit, Integer validityPeriod) {
        this.name = name;
        this.price = price;
        this.setTrafficLimit(trafficLimit);
        this.setSpeedLimit(speedLimit);
        this.ipLimit = ipLimit;
        this.validityPeriod = validityPeriod;
    }

    private void setTrafficLimit(@Pattern(regexp = Constants.BANDWIDTH_REGEX, message = "quota must end with 'M' or 'G' character") String quotaStr) {
        int quota = Integer.valueOf(quotaStr.substring(0, quotaStr.length() - 1));
        if (StringUtils.upperCase(quotaStr).endsWith(MEGABYTE) || StringUtils.upperCase(quotaStr).endsWith(MEGABYTE_SHORT)) {
            this.trafficLimit = (long) quota * 1024 * 1024;
        }
        if (StringUtils.upperCase(quotaStr).endsWith(GIGABYTE) || StringUtils.upperCase(quotaStr).endsWith(GIGABYTE_SHORT)) {
            this.trafficLimit = (long) quota * 1024 * 1024 * 1024;
        }
    }

    private void setSpeedLimit(String speedStr) {
        int speed = Integer.valueOf(speedStr.substring(0, speedStr.length() - 1));
        if (StringUtils.upperCase(speedStr).endsWith(MEGABYTE) || StringUtils.upperCase(speedStr).endsWith(MEGABYTE_SHORT)) {
            this.speedLimit = (long) speed * 1024 * 1024;
        }
        if (StringUtils.upperCase(speedStr).endsWith(GIGABYTE) || StringUtils.upperCase(speedStr).endsWith(GIGABYTE_SHORT)) {
            this.speedLimit = (long) speed * 1024 * 1024 * 1024;
        }
    }
}
