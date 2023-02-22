package com.rocketpt.server.torrent.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    MOVIES(401),
    TVSERIES(402),
    DOCUMENTARIES(403),
    MUSIC(406),
    TVSHOWS(416),
    ANIMATIONS(417),
    MUSICVIDEOS(407),
    CONCERT(408),
    EDUCATION(404),
    AUDIOBOOKS(405),
    DRAMA(409),
    SPORTS(418),
    SOFTWARE(419),
    GAMES(421),
    EBOOKS(423),
    OTHERS(410);

    @EnumValue
    private final int code;

    @JsonCreator
    public static Category byCode(final int code) {
        for (final Category c: Category.values()) {
            if (c.code == code) return c;
        }
        return null;
    }
}