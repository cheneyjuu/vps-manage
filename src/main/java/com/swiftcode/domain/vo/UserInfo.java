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
public class UserInfo extends AbstractVo {
    private String login;

    private UserInfo() {
        this.login = null;
    }
}
