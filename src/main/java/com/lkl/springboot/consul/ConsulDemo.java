package com.lkl.springboot.consul;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;

public class ConsulDemo {

    public static void main(String[] args) throws NotRegisteredException {
        Consul consul = Consul.newClient("localhost", 8500); // connect to Consul on localhost
        final AgentClient agentClient = consul.agentClient();

        String serviceName = "MyService";
        final String serviceId = "1";

        agentClient.register(8080, 3L, serviceName, serviceId); // registers with a TTL of 3 seconds
        agentClient.pass(serviceId); // check in with Consul, serviceId required only.  client will prepend "service:" for service level checks.
        Timer timer = new Timer();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    if (new Date().before(sdf.parse("2015-09-01 16:57:00"))) {
                        agentClient.pass(serviceId, "it's ok " + new Date());
                        System.out.println("it's ok " + new Date());
                    } else {
                        agentClient.fail("it's fail " + new Date());
                    }

                } catch (NotRegisteredException e) {
                    System.out.println("----fail----");
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 3000, 2000);

    }
}