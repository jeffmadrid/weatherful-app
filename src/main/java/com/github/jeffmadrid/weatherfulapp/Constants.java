package com.github.jeffmadrid.weatherfulapp;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class Constants {
    public static final String API_KEY_HEADER = "X-Api-Key";

    public static final AntPathRequestMatcher V1_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/v1/**");
}
