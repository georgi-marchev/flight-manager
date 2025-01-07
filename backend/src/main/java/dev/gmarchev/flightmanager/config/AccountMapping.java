package dev.gmarchev.flightmanager.config;

import dev.gmarchev.flightmanager.dto.account.AccountCreateRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounts")
public record AccountMapping(AccountCreateRequest admin) {}
