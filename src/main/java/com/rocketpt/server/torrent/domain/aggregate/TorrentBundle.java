package com.rocketpt.server.torrent.domain.aggregate;

import com.dampcake.bencode.Type;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.Constants;
import com.rocketpt.server.common.SessionItemHolder;
import com.rocketpt.server.common.base.I18nMessage;
import com.rocketpt.server.common.exception.RocketPTException;
import com.rocketpt.server.dto.sys.UserinfoDTO;
import com.rocketpt.server.torrent.domain.context.TorrentBundleContext;
import com.rocketpt.server.torrent.domain.context.TorrentContext;
import com.rocketpt.server.torrent.domain.entity.Torrent;
import com.rocketpt.server.torrent.domain.entity.TorrentFile;
import com.rocketpt.server.torrent.util.BencodeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yzr
 */
@Slf4j
@Data
public class TorrentBundle {
    private Torrent torrent;
    private TorrentFile torrentFile;

    public void parse(TorrentBundleContext torrentBundleContext) {
        byte[] torrentBytes = torrentBundleContext.getTorrentBytes();
        Map<String, Object> decodeDict = BencodeUtil.decodeWithUTF8(torrentBytes, Type.DICTIONARY);
        checkCompatibility(decodeDict);
        Map<String, Object> infoDict = (Map<String, Object>) decodeDict.get("info");
        TorrentContext torrentContext = torrentBundleContext.getTorrentContext();
        torrentContext.setFileSize(calculateSize(infoDict));
        torrentContext.setFileCount(calculateCount(infoDict));
        torrentContext.setTorrentMap(decodeDict);
        torrentBundleContext.setTorrentContext(torrentContext);
    }

    public void transform(TorrentBundleContext torrentBundleContext) {
        byte[] torrentBytes = torrentBundleContext.getTorrentBytes();
        Map<String, Object> map = BencodeUtil.decodeWithISO8859(torrentBytes, Type.DICTIONARY);
        map.remove("announce-list");
        map.remove("announce");
        Map<String, Object> infoMap = (Map<String, Object>) map.get("info");
        infoMap.put("private", 1);
        infoMap.put("source", Constants.Source.PREFIX + Constants.Source.NAME);
        map.put("info", infoMap);
        //todo parse torrent version
        torrentFile = new TorrentFile(null, BencodeUtil.encodeWithISO8859(map, Type.DICTIONARY), TorrentFile.IdentityType.V1);
    }

    public byte[] infoHash() {
        try {
            Map<String, Object> decodedMap = BencodeUtil.decodeWithISO8859(torrentFile.getTorrentBytes(), Type.DICTIONARY);
            Map<String, Object> infoDecodedMap = (Map<String, Object>) decodedMap.get("info");
            byte[] encode = BencodeUtil.encodeWithISO8859(infoDecodedMap, Type.DICTIONARY);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(encode);
        } catch (NoSuchAlgorithmException e) {
            log.error("");
            throw new RocketPTException(CommonResultStatus.SERVER_ERROR, "");
        }
    }

    public void brand(TorrentBundleContext torrentBundleContext) {
        Map<String, Object> decodedMap = BencodeUtil.decodeWithISO8859(torrentBundleContext.getTorrentBytes(), Type.DICTIONARY);
        UserinfoDTO dto = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
        decodedMap.put("announce", Constants.Announce.PROTOCOL + "://" + Constants.Announce.HOSTNAME + ":" + Constants.Announce.PORT + "/" + dto.credential().passkey());
        torrentBundleContext.setTorrentBytes(BencodeUtil.encodeWithISO8859(decodedMap, Type.DICTIONARY));
    }

    private void checkCompatibility(Map<String, Object> map) {
        if (map.containsKey("piece layers") || map.containsKey("files tree") || map.containsKey("meta version") && (int) map.get("meta version") == 2) {
            throw new RocketPTException(CommonResultStatus.PARAM_ERROR, I18nMessage.getMessage("protocol_v2_not_support"));
        }
    }

    private long calculateSize(Map<String, Object> infoMap) {
        long size;
        if (infoMap.containsKey("length")) {
            size = (Long) infoMap.get("length");
        } else {
            List<Map<String, Object>> files = (List<Map<String, Object>>) infoMap.get("files");
            size = files.stream().map(f -> (Long) f.get("length")).collect(Collectors.summingLong(i -> i));
        }
        return size;
    }

    private int calculateCount(Map<String, Object> infoMap) {
        int count;
        if (infoMap.containsKey("length")) {
            count = 1;
        } else {
            List<Map<String, Object>> files = (List<Map<String, Object>>) infoMap.get("files");
            count = files.size();
        }
        return count;
    }
}
