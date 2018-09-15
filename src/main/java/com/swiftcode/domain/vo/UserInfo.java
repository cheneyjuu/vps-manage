package com.swiftcode.domain.vo;

import com.swiftcode.domain.Server;
import com.swiftcode.domain.base.AbstractVo;
import com.swiftcode.service.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author chen
 */
@Embeddable
@Value
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserInfo extends AbstractVo {
    private String login;
    /**
     * 用户已用流量
     */
    private String bandwidth;
    private Integer port;
    private String password;
    private String hostIP;

    public UserInfo(String login) {
        this.login = login;
        this.bandwidth = null;
        this.port = null;
        this.password = null;
        this.hostIP = null;
    }

    private UserInfo() {
        this.login = null;
        this.bandwidth = null;
        this.port = null;
        this.password = null;
        this.hostIP = null;
    }
}
