package com.rocketpt.server.torrent.repo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.Constants;
import com.rocketpt.server.common.SessionItemHolder;
import com.rocketpt.server.common.base.I18nMessage;
import com.rocketpt.server.common.exception.RocketPTException;
import com.rocketpt.server.torrent.mapper.TorrentMapper;
import com.rocketpt.server.dto.sys.UserinfoDTO;
import com.rocketpt.server.torrent.mapper.TorrentFileMapper;
import com.rocketpt.server.torrent.domain.context.TorrentBundleContext;
import com.rocketpt.server.torrent.domain.context.TorrentContext;
import com.rocketpt.server.torrent.domain.entity.Torrent;
import com.rocketpt.server.torrent.domain.entity.TorrentFile;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Repository
public class TorrentRepository {
    @Resource
    private TorrentMapper torrentMapper;

    @Resource
    private TorrentFileMapper torrentFileMapper;

    @Transactional(rollbackFor = SQLException.class)
    public void save(TorrentBundleContext torrentBundleContext, TorrentFile torrentFile) {
        Map<String, Object> parsedMap = torrentBundleContext.getTorrentContext().getTorrentMap();
        Map<String, Object> infoParsedMap = (Map<String, Object>) parsedMap.get("info");
        Torrent entity = new Torrent();
        BeanUtil.copyProperties(torrentBundleContext.getTorrentContext(), entity, "id","infoHash","added","visible","approvalStatus");
        entity.setInfoHash(torrentBundleContext.getInfoHash());
        String name = (String) infoParsedMap.get("name");
        entity.setName(name);
        entity.setFilename(Constants.Source.PREFIX + name + ".torrent");
        entity.setType(torrentBundleContext.getTorrentContext().getFileCount() > 1 ? Torrent.Type.multi : Torrent.Type.single);
        UserinfoDTO userinfoDTO = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
        entity.setOwner(userinfoDTO.userId());
        entity.setAdded(new Date());
        torrentMapper.insert(entity);
        persist(entity.getId(), torrentFile);
    }

    public TorrentBundleContext fetch(Long torrentId) {
        Optional<Torrent> torrentOptional = Optional.ofNullable(torrentMapper.selectById(torrentId));
        Optional<TorrentFile> fileOptional = Optional.ofNullable(torrentFileMapper.selectOne(Wrappers.<TorrentFile>lambdaQuery().eq(TorrentFile::getTorrentId, torrentId)));
        if (torrentOptional.isEmpty() || fileOptional.isEmpty())
            throw new RocketPTException(CommonResultStatus.PARAM_ERROR, I18nMessage.getMessage("torrent_not_exists"));
        TorrentBundleContext torrentBundleContext = new TorrentBundleContext();
        TorrentContext torrentContext = new TorrentContext();
        BeanUtil.copyProperties(torrentOptional.get(), torrentContext);
        torrentBundleContext.setTorrentContext(torrentContext);
        torrentBundleContext.setTorrentBytes(fileOptional.get().getTorrentBytes());
        return torrentBundleContext;
    }

    private void persist(Long torrentId, TorrentFile torrentFile) {
        torrentFile.setTorrentId(torrentId);
        torrentFileMapper.insert(torrentFile);
    }
}
