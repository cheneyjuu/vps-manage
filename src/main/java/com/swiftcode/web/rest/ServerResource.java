package com.swiftcode.web.rest;

import com.google.common.collect.Maps;
import com.swiftcode.domain.Server;
import com.swiftcode.domain.vo.UserInfo;
import com.swiftcode.security.SecurityUtils;
import com.swiftcode.service.ServerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * @author chen
 */
@RestController
@RequestMapping(value = "/api")
public class ServerResource {
    private final ServerService serverService;

    @Autowired
    public ServerResource(ServerService serverService) {
        this.serverService = serverService;
    }

    /**
     * 用户获取指定服务器的端口和密码信息.
     *
     * @param serverId 服务器id
     * @return 端口和密码信息
     */
    @GetMapping("/servers/{id}/account")
    public ResponseEntity<?> userGetAccount(@PathVariable("id") Long serverId) {
        Server aServer = serverService.bindUserAndServer(serverId);
        String currentUser = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalArgumentException("invalid user"));
        UserInfo userInfo = aServer.getUserInfo(currentUser);
        Map<String, String> map = Maps.newHashMap();
        if (userInfo == null) {
            map.put("errCode", "1");
            map.put("errMsg", "无效的用户");
            return ResponseEntity.badRequest().body(map);
        }
        map.put("port", String.valueOf(userInfo.getPort()));
        map.put("password", userInfo.getPassword());
        return ResponseEntity.ok(map);
    }

    /**
     * 新增服务器.
     *
     * @param server 服务器信息
     * @return 已新增的服务器
     */
    @SneakyThrows
    @PostMapping("/servers")
    public ResponseEntity<?> addServer(@RequestBody Server server) {
        Server aServer = serverService.addServer(server);
        return ResponseEntity.created(new URI("")).body(aServer);
    }
}
