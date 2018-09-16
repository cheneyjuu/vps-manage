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
     * @param serverId 服务器ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Server bindUserAndServer(Long serverId) {
        String currentUser = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalArgumentException("invalid user"));
        log.info("current user: {}", currentUser);

        Server server = serverRepository.findById(serverId).orElseThrow(IllegalArgumentException::new);
        Pair<Integer, String> portPassword = server.portPassword();
        UserInfo userInfo = new UserInfo(currentUser, server.getBandwidth(), portPassword.getKey(),
            portPassword.getValue(), server.getHostIP());
        server.addUserInfo(userInfo);
        return serverRepository.save(server);
    }

    @Transactional(rollbackFor = Exception.class)
    public Server addServer(Server server) {
        return serverRepository.save(server);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Server server(Long id) {
        return serverRepository.findById(id).orElse(null);
    }
}
