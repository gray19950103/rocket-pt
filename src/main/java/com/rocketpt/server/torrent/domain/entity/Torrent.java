package com.rocketpt.server.torrent.domain.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.rocketpt.server.dto.entity.EntityBase;
import com.rocketpt.server.torrent.domain.enums.Category;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author plexpt
 * @email plexpt@gmail.com
 * @date 2023-02-09 14:43:20
 */
@Data
@TableName("torrent")
public class Torrent extends EntityBase {
    /**
     * 种子哈希
     */
    private byte[] infoHash;
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
     * 封面
     */
    private String cover;
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
     * 添加日期
     */
    private Date added;
    /**
     * 类型
     */
    private Type type;
    /**
     * 评论数
     */
    private Integer comments;
    /**
     * 浏览次数
     */
    private Integer views;
    /**
     * 点击次数
     */
    private Integer hits;
    /**
     * 完成次数
     */
    private Integer timesCompleted;
    /**
     * 下载数
     */
    private Integer leechers;
    /**
     * 做种数
     */
    private Integer seeders;
    /**
     * 最后操作日期
     */
    private Date lastAction;
    /**
     * 可见性
     */
    private String visible;
    /**
     *
     */
    private String banned;
    /**
     * 拥有者
     */
    private Long owner;
    /**
     *
     */
    private Integer spState;
    /**
     * 促销时间类型
     */
    private Integer promotionTimeType;
    /**
     * 促销截止日期
     */
    private Date promotionUntil;
    /**
     *
     */
    private Integer url;
    /**
     * 位置状态
     */
    private String posState;
    /**
     * 位置状态截止日期
     */
    private Date posStateUntil;
    /**
     *
     */
    private Integer cacheStamp;
    /**
     * 推荐类型
     */
    private String picktype;
    /**
     * 推荐日期
     */
    private Date picktime;
    /**
     * 最后做种日期
     */
    private Date lastReseed;
    /**
     * ptgen生成内容
     */
    private String ptGen;
    /**
     *
     */
    private String technicalInfo;
    /**
     *
     */
    private Integer hr;
    /**
     * 审批状态
     */
    private Integer approvalStatus;

    @RequiredArgsConstructor
    public enum Type {
        single(1),
        multi(2);

        @EnumValue
        private final int code;
    }

}
