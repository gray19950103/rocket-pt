package com.rocketpt.server.torrent.domain.context;

import lombok.Data;

@Data
public class TorrentBundleContext {
    private TorrentContext torrentContext;
    private byte[] torrentBytes;
    private byte[] infoHash;
}
