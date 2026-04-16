package com.project.deployment_system.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.deployment_system.model.Deployment;
import com.project.deployment_system.model.DeploymentStatus;
import com.project.deployment_system.repository.DeploymentRepository;

@Service
public class DeploymentService {

    private final DeploymentRepository repository;

    @Autowired
    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.repository = deploymentRepository;
    }

    public Deployment createDeployment(String serviceName, String version) {
        Deployment deployment = Deployment.builder()
                .serviceName(serviceName)
                .version(version)
                .status(DeploymentStatus.DEPLOYING)
                .environment("BLUE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return repository.save(deployment);
    }

}
