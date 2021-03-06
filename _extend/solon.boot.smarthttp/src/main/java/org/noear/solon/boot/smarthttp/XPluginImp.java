package org.noear.solon.boot.smarthttp;

import org.noear.solon.XApp;
import org.noear.solon.boot.smarthttp.http.SmartHttpContextHandler;
import org.noear.solon.boot.smarthttp.http.XFormContentFilter;
import org.noear.solon.boot.smarthttp.websocket.WebSocketHandleImp;
import org.noear.solon.core.XMethod;
import org.noear.solon.core.XPlugin;
import org.smartboot.http.HttpBootstrap;

public final class XPluginImp implements XPlugin {

    HttpBootstrap _server = null;

    public static String solon_boot_ver(){
        return "smart http 1.0.7/" + XApp.cfg().version();
    }

    @Override
    public void start(XApp app) {
        if (app.enableHttp() == false) {
            return;
        }

        XServerProp.init();

        long time_start = System.currentTimeMillis();

        SmartHttpContextHandler _handler = new SmartHttpContextHandler();

        _server = new HttpBootstrap();
        _server.setBannerEnabled(false);
        _server.pipeline().next(_handler);

        if (app.enableWebSocket()) {
            _server.wsPipeline().next(new WebSocketHandleImp());
        }


        System.out.println("solon.Server:main: SmartHttpServer 1.0.7(smarthttp)");

        try {

            _server.setThreadNum(Runtime.getRuntime().availableProcessors() + 2)
                    .setPort(app.port())
                    .start();

            app.before("**", XMethod.ALL, -9, new XFormContentFilter());

            long time_end = System.currentTimeMillis();

            System.out.println("solon.Connector:main: smarthttp: Started ServerConnector@{HTTP/1.1,[http/1.1]}{0.0.0.0:" + app.port() + "}");
            System.out.println("solon.Server:main: smarthttp: Started @" + (time_end - time_start) + "ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() throws Throwable {
        if (_server != null) {
            _server.shutdown();
            _server = null;

            System.out.println("solon.Server:main: smarthttp: Has Stopped " + solon_boot_ver());
        }
    }
}

