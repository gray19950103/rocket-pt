package com.rocketpt.server.sys.event;

import com.rocketpt.server.common.DomainEvent;
import com.rocketpt.server.sys.entity.Role;

/**
 * @author plexpt
 */
public record RoleDeleted(Role role) implements DomainEvent {
}