package com.swiftcode.service;

import com.swiftcode.domain.Server;
import com.swiftcode.domain.vo.UserInfo;
import com.swiftcode.repository.ServerRepository;
import com.swiftcode.security.SecurityUtils;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chen
 */
@Service
@Slf4j
public class ServerService {
    private final ServerRepository serverRepository;

    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * 绑定用户和服务器.
     * 用户查看特定服务器的账号和密码时，绑定用户信息和服务器信息
     *
     * @param server 服务器
     */
    @Transactional(rollbackFor = Exception.class)
    public Server bindUser(Server server) {
        String currentUser = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalArgumentException("invalid user"));
        log.info("current user: {}", currentUser);
        Pair<Integer, String> portPassword = server.portPassword();
        UserInfo userInfo = new UserInfo(currentUser, server.getBandwidth(), portPassword.getKey(),
            portPassword.getValue(), server.getHostIP());
        server.getUserInfoSet().add(userInfo);
        return serverRepository.save(server);
    }
}
