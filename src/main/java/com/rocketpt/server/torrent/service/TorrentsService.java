package com.rocketpt.server.torrent.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.base.Res;
import com.rocketpt.server.common.exception.RocketPTException;
import com.rocketpt.server.torrent.mapper.TorrentMapper;
import com.rocketpt.server.torrent.domain.aggregate.TorrentBundle;
import com.rocketpt.server.torrent.domain.context.TorrentBundleContext;
import com.rocketpt.server.torrent.domain.context.TorrentContext;
import com.rocketpt.server.torrent.domain.entity.Torrent;
import com.rocketpt.server.torrent.repo.TorrentRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author plexpt
 * @email plexpt@gmail.com
 * @date 2023-01-28 00:01:53
 */
@Service
public class TorrentsService extends ServiceImpl<TorrentMapper, Torrent> {
    @Resource
    private TorrentRepository torrentRepository;

    public Res upload(byte[] bytes, TorrentContext torrentContext) {
        TorrentBundle torrentBundle = new TorrentBundle();
        TorrentBundleContext torrentBundleContext = new TorrentBundleContext();
        torrentBundleContext.setTorrentContext(torrentContext);
        torrentBundleContext.setTorrentBytes(bytes);
        torrentBundle.transform(torrentBundleContext);
        byte[] infoHash = torrentBundle.infoHash();
        checkExistence(infoHash);
        torrentBundleContext.setInfoHash(infoHash);
        torrentBundle.parse(torrentBundleContext);
        torrentRepository.save(torrentBundleContext, torrentBundle.getTorrentFile());
        return Res.ok();
    }

    public TorrentBundleContext download(Long torrentId) {
        TorrentBundleContext torrentBundleContext = torrentRepository.fetch(torrentId);
        TorrentBundle torrentBundle = new TorrentBundle();
        torrentBundle.brand(torrentBundleContext);
        return torrentBundleContext;
    }

    private void checkExistence(byte[] bytes) {
        if (count(Wrappers.<Torrent>lambdaQuery().eq(Torrent::getInfoHash, bytes)) != 0)
            throw new RocketPTException(CommonResultStatus.PARAM_ERROR, "该种子站内已存在。");
    }
}

