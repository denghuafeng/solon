package org.noear.solon.boot.wizzardohttp;

import com.wizzardo.http.HttpConnection;
import com.wizzardo.http.HttpServer;
import org.noear.solon.XApp;
import org.noear.solon.core.XPlugin;


public final class XPluginImp implements XPlugin {
    private HttpServer<HttpConnection> _server = null;

    public static String solon_boot_ver(){
        return "Wizzardo.http 0.4/" + XApp.cfg().version();
    }

    @Override
    public  void start(XApp app) {
        if(app.enableHttp() == false){
            return;
        }

        XServerProp.init();

        _server = new HttpServer<>(app.port());

        long time_start = System.currentTimeMillis();

        _server.getUrlMapping().append("*", new WizzContextHandler());

        System.out.println("solon.Server:main: Wizzardo.http 0.4(wzhttp)");

        try {
            _server.setPort(app.port());
            _server.start();

            long time_end = System.currentTimeMillis();

            System.out.println("solon.Connector:main: wzhttp: Started ServerConnector@{HTTP/1.1,[http/1.1]}{0.0.0.0:" + app.port() + "}");
            System.out.println("solon.Server:main: wzhttp: Started @" + (time_end - time_start) + "ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() throws Throwable {
        if(_server != null) {
            _server = null;

            System.out.println("solon.Server:main: wzhttp: Has Stopped " + solon_boot_ver());
        }
    }
}