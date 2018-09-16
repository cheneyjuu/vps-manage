package com.swiftcode.service;

import com.swiftcode.VpsManageApp;
import com.swiftcode.domain.Server;
import com.swiftcode.repository.ServerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VpsManageApp.class)
@Transactional
public class ServerServiceTest {
    @Autowired
    private ServerRepository serverRepository;
    @Autowired
    private ServerService serverService;

    public static Server createServer() {
        String hostIP = "192.168.1.100";
        Integer sshPort = 22;
        String user = "root";
        String password = "root";
        return new Server(hostIP, sshPort, user, password);
    }

    @Before
    public void setUp() throws Exception {
        serverRepository.save(createServer());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testBindUserToServer() {
        Server userBindServer = serverService.bindUserAndServer(1L);

        assertThat(userBindServer.getUserInfoSet()).hasSize(1);
    }
}
