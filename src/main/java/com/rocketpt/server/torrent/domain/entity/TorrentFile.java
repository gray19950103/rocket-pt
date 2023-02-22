package com.rocketpt.server.torrent.domain.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rocketpt.server.dto.entity.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 种子文件 torrent_file
 *
 * @author yzr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("torrent_file")
public class TorrentFile extends EntityBase {

    /**
     * 对应种子id
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long torrentId;

    /**
     * 二进制种子文件
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY)
    private byte[] torrentBytes;

    /**
     * 种子协议版本
     */
    private IdentityType identityType;

    @RequiredArgsConstructor
    public enum IdentityType {
        V1(0),
        V2_ONLY(1),
        V2_COMPATIBILITY(2);

        @EnumValue
        //标记数据库存的值是code
        private final int code;

    }
}
