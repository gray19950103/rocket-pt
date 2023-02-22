package com.rocketpt.server.sys.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rocketpt.server.torrent.domain.entity.TorrentFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yzr
 */
@Mapper
public interface TorrentFileMapper extends BaseMapper<TorrentFile> {
}
