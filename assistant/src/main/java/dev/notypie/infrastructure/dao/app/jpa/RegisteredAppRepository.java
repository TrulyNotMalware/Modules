package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.infrastructure.dao.app.jpa.schema.RegisteredApp;
import org.springframework.data.jpa.repository.JpaRepository;

interface RegisteredAppRepository extends JpaRepository<RegisteredApp, String> {

}