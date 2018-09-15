package com.swiftcode.domain

import spock.lang.Specification

class ServerSpec extends Specification {

    def "Administrator add new node"() {
        given: "server info"
        def hostIP = "192.168.1.100"
        def sshPort = 22
        def user = "root"
        def password = "root"
        and: "network info of server"
        def resetDay = 25
        def bandwidth = "366GB"
        def network = "1GB"

        when: "add new server"
        def server = new Server(hostIP, sshPort, user, password)
        and: "add network info of server"
        server.addNetworkInfo(network, bandwidth, resetDay)

        then: "server be added"
        server != null
        server.hostIP == hostIP
        server.bandwidth == bandwidth
    }

    def "user get port and password"() {
        given: "a server"
        def hostIP = "192.168.1.100"
        def sshPort = 22
        def user = "root"
        def password = "root"
        def server = new Server(hostIP, sshPort, user, password)

        when: "user get port and password"
        def portPassword = server.portPassword()
        def port = portPassword.getKey()
        def pwd = portPassword.getValue()

        then: "user have a port and password"
        port != null
        pwd != null
    }
}
