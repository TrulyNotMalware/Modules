package dev.notypie.infrastructure.dao.app.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

interface RegisteredAppRepository extends JpaRepository<RegisteredApp, String> {

}