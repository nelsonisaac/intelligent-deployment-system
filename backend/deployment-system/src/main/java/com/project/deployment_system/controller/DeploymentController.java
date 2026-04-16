package com.project.deployment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.deployment_system.model.Deployment;
import com.project.deployment_system.service.DeploymentService;

@RestController
@RequestMapping("/api/v1/deployments")
public class DeploymentController {

    private final DeploymentService deploymentService;

    @Autowired
    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @PostMapping("/create")
    public Deployment createDeployment(@RequestParam String serviveName,
            @RequestParam String version) {
        return deploymentService.createDeployment(serviveName, version);
    }

}
