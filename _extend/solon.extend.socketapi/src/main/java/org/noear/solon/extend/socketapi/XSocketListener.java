package org.noear.solon.extend.socketapi;

/**
 * XSocket 监听者
 *
 * @author noear
 * @since 1.0
 * */
@FunctionalInterface
public interface XSocketListener {
    default void onOpen(XSession session){}

    void onMessage(XSession session, XSocketMessage message);

    default void onClosing(XSession session){}

    default void onClose(XSession session){}

    default void onError(XSession session, Throwable error){}
}