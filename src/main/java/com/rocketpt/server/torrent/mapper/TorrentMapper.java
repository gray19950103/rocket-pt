package com.rocketpt.server.torrent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rocketpt.server.torrent.domain.entity.Torrent;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author plexpt
 * @email plexpt@gmail.com
 * @date 2023-01-28 00:01:53
 */
@Mapper
public interface TorrentMapper extends BaseMapper<Torrent> {

}
