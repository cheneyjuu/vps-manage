package com.swiftcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swiftcode.config.Constants;
import com.swiftcode.domain.base.AbstractEntity;
import com.swiftcode.domain.vo.UserInfo;
import com.swiftcode.service.util.RandomUtil;
import javafx.util.Pair;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author chen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sc_server")
public class Server extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = Constants.IP_REGEX)
    @Column(length = 15, unique = true, nullable = false)
    private String hostIP;
    @NotNull
    @Size(min = 3, max = 5)
    @Column(length = 5, nullable = false)
    private Integer sshPort;
    @NotNull
    @Column(length = 10, nullable = false)
    @Pattern(regexp = Constants.LOGIN_REGEX)
    private String user;
    @JsonIgnore
    @NotNull
    private String password;
    private String network;
    private String bandwidth;
    private Integer resetDay;
    @ElementCollection
    @CollectionTable(name = "sc_server_users", joinColumns = @JoinColumn(name = "server_id"))
    @Column(name = "user_id")
    private Set<UserInfo> userInfoSet;

    private Server() {
    }

    public Server(@NotNull @Pattern(regexp = Constants.IP_REGEX) String hostIP,
                  @NotNull @Size(min = 3, max = 5) Integer sshPort,
                  @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) String user,
                  @NotNull String password) {
        this.hostIP = hostIP;
        this.sshPort = sshPort;
        this.user = user;
        this.password = password;
    }

    public void addNetworkInfo(String network, String bandwidth, Integer resetDay) {
        this.network = network;
        this.bandwidth = bandwidth;
        this.resetDay = resetDay;
    }

    public Pair<Integer, String> portPassword() {
        Integer port = RandomUtils.nextInt(30000, 60000);
        String password = RandomUtil.generatePassword();
        return new Pair<>(port, password);
    }

//    public UserInfo generatePortPassword(UserInfo userInfo) {
//        userInfo
//    }
}
