package com.rocketpt.server.torrent.domain.context;

import com.rocketpt.server.torrent.domain.enums.Category;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.util.Map;

@Data
public class TorrentContext {
    /**
     * 名称
     */
    private String name;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 文件名
     */
    private String filename;
    /**
     * IMDb链接
     */
    private String imdbLink;
    /**
     * 描述
     */
    private String description;
    /**
     * 类别
     */
    private Category category;
    /**
     * 来源媒介
     */
    private Integer medium;
    /**
     * 视频编码
     */
    private Integer videoCodec;
    /**
     * 音频编码
     */
    private Integer audioCodec;
    /**
     * 分辨率
     */
    private Integer resolution;
    /**
     * 地区
     */
    private Integer processing;
    /**
     * 制作组
     */
    private Integer team;
    /**
     * 匿名
     */
    private Boolean anonymous;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件数量
     */
    private Integer fileCount;
    /**
     * 解析map
     */
    @Hidden
    private Map<String, Object> torrentMap;
}
