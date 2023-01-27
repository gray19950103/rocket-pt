package com.rocketpt.server.sys.event;

import com.rocketpt.server.common.DomainEvent;
import com.rocketpt.server.sys.entity.User;

/**
 * @author plexpt
 */
public record UserDeleted(User user) implements DomainEvent {
}
