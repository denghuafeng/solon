package org.noear.fairy;

import java.util.Map;

/**
 * 过滤器
 * */
public interface IFilter {
    /**
     * 过滤
     */
    void filter(FairyConfig cfg, String method, String url, Map<String, String> headers, Map<String, Object> args);
}
