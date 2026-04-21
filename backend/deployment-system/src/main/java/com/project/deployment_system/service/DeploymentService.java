package com.project.deployment_system.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.deployment_system.model.Deployment;
import com.project.deployment_system.model.DeploymentStatus;
import com.project.deployment_system.repository.DeploymentRepository;

@Service
public class DeploymentService {

    private final DeploymentRepository repository;

    private final NginxService nginxService;

    @Autowired
    public DeploymentService(DeploymentRepository deploymentRepository, NginxService nginxService) {
        this.repository = deploymentRepository;
        this.nginxService = nginxService;
    }

    public Deployment createDeployment(String serviceName, String version) {
        Deployment deployment = Deployment.builder()
                .serviceName(serviceName)
                .version(version)
                .status(DeploymentStatus.PENDING)
                .environment("BLUE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return repository.save(deployment);
    }

    public Deployment startDeployment(Long id) {
        Deployment deployment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deployment not found"));

        deployment.setStatus(DeploymentStatus.DEPLOYING);
        deployment.setUpdatedAt(LocalDateTime.now());
        repository.save(deployment);

        String nextEnv = deployment.getEnvironment().equals("BLUE")?"GREEN":"BLUE";
        boolean success = executeDeploymentScript(nextEnv);

        if (success) {
            deployment.setStatus(DeploymentStatus.SUCCESS);
            deployment.setEnvironment(nextEnv);
            nginxService.switchTraffic(nextEnv);

        } else {
            deployment.setStatus(DeploymentStatus.FAILED);
            rollBackDeployment();
            deployment.setStatus(DeploymentStatus.ROLLED_BACK);
        }
        deployment.setUpdatedAt(LocalDateTime.now());
        return repository.save(deployment);
    }

    public boolean executeDeploymentScript(String nextEnv) {
        try {
            String deployScript = "scripts/deploy.sh";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", deployScript, nextEnv);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("DEPLOY LOGS:" + line);
            }

            int exitCode = process.waitFor();
            return exitCode == 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void rollBackDeployment() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "scripts\\rollback.sh");
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("ROLLBACK LOGS:" + line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("ROLLBACK FAILED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
