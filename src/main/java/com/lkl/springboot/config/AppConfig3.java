package com.lkl.springboot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my")
public class AppConfig3 {

    private List<String> servers = new ArrayList<String>();

    public List<String> getServers() {
        return this.servers;
    }

    @Override
    public String toString() {
        return "AppConfig3 [servers=" + servers + "]";
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

}
