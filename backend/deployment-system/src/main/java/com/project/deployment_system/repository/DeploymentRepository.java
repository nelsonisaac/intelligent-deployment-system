package com.project.deployment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.deployment_system.model.Deployment;

@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {

}
