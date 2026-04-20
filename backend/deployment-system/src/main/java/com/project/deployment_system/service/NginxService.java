package com.project.deployment_system.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public class NginxService {

    private static final String TEMPLATE_PATH = "scripts/nginx.conf.template";
    private static final String OUTPUT_PATH = "scripts/nginx.conf";

    public void switchTraffic(String activeEnv){
        try{
            String content = Files.readString(Path.of(TEMPLATE_PATH));
            String active = activeEnv.equals("BLUE")?"blue-app":"green-app";
            String updated = content.replace("{{ACTIVE}}", active);
            Files.writeString(Path.of(OUTPUT_PATH), updated);

            reloadNginx();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void reloadNginx(){
        try{
            ProcessBuilder pb = new ProcessBuilder("nginx", "-s", "reload");
            Process process = pb.start();
            int exitCode = process.waitFor();
            if(exitCode != 0){
                System.err.println("Failed to reload Nginx");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
