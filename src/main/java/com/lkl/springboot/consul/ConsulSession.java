package com.lkl.springboot.consul;

import com.google.common.base.Optional;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.SessionClient;

public class ConsulSession {

    public static void main(String[] args) throws NotRegisteredException {
        Consul consul = Consul.newClient("localhost", 8500); // connect to Consul on localhost
        SessionClient session = consul.sessionClient();

        KeyValueClient kv = consul.keyValueClient();
        // kv.releaseLock(key, sessionId)
        //  System.out.println(kv.acquireLock("hello", "91b33042-c9ff-5e95-bdf7-9562e21e9e3b"));
        Optional<String> os = kv.getSession("hello");
    }
}