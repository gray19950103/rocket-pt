package com.rocketpt.server.sys.dto;

import java.util.List;

import com.rocketpt.server.sys.entity.Resource.Type;

/**
 * @author plexpt
 */
public record ResourceTreeDTO(Long id, String name, Type type, String permission, String url, String icon,
                              List<ResourceTreeDTO> children, Long parentId, String parentName) {

}
