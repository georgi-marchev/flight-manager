package dev.gmarchev.flightmanager.config;

import dev.gmarchev.flightmanager.dto.AccountRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounts")
public record AccountMapping(AccountRequest admin) {}
