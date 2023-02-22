package com.rocketpt.server.torrent.util;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import com.rocketpt.server.common.CommonResultStatus;
import com.rocketpt.server.common.exception.RocketPTException;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@UtilityClass
public class BencodeUtil {
    private final Bencode bencode = new Bencode(StandardCharsets.UTF_8);
    private final Bencode infoBencode = new Bencode(StandardCharsets.ISO_8859_1);

    public static <T> byte[] encodeWithUTF8(T data, Type<T> type) {
        return encode(bencode, data, type);
    }

    public static <T> T decodeWithUTF8(byte[] bytes, Type<T> type) {
        return bencode.decode(bytes, type);
    }

    public static <T> byte[] encodeWithISO8859(T data, Type<T> type) {
        return encode(infoBencode, data, type);
    }

    public static <T> T decodeWithISO8859(byte[] bytes, Type<T> type) {
        return infoBencode.decode(bytes, type);
    }

    private <T> byte[] encode(Bencode coder, T data, Type<T> type) {
        if (type == Type.STRING)
            return coder.encode((String) data);
        else if (type == Type.NUMBER)
            return coder.encode((Number) data);
        else if (type == Type.LIST)
            return coder.encode((Iterable) data);
        else if (type == Type.DICTIONARY)
            return coder.encode((Map) data);
        else throw new RocketPTException(CommonResultStatus.SERVER_ERROR, "Exception thrown during encoding");
    }
}
