package org.noear.solon.boot.undertow;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.*;
import org.noear.solon.XApp;
import org.noear.solon.boot.undertow.http.UtHttpHandlerJsp;
import org.noear.solon.boot.undertow.websocket.UtWsConnectionCallback;
import org.noear.solon.core.XPlugin;

import static io.undertow.Handlers.websocket;

/**
 * @author  by: Yukai
 * @since : 2019/3/28 15:49
 */
public class XPluginUndertow extends XPluginUndertowBase implements XPlugin {
    Undertow _server;
    @Override
    public void start(XApp app) {
        try {
            setup(app);

            _server.start();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void stop() throws Throwable {
        if (_server != null) {
            _server.stop();
            _server = null;
        }
    }

    protected void setup(XApp app) throws Throwable {
        // 动作分发Handler
        HttpHandler httpHandler = buildHandler();

        //************************** init server start******************
        Undertow.Builder builder = Undertow.builder();

        builder.setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false);

        builder.addHttpListener(app.port(), "0.0.0.0");

        if (app.enableWebSocket()) {
            builder.setHandler(websocket(new UtWsConnectionCallback(), httpHandler));
        } else {
            builder.setHandler(httpHandler);
        }

        _server = builder.build();

        //************************* init server end********************
    }

    protected HttpHandler buildHandler() throws Exception {
        DeploymentInfo builder = initDeploymentInfo();

        //添加servlet
        builder.addServlet(new ServletInfo("ACTServlet", UtHttpHandlerJsp.class).addMapping("/"));
        //builder.addInnerHandlerChainWrapper(h -> handler); //这个会使过滤器不能使用


        //开始部署
        final ServletContainer container = Servlets.defaultContainer();
        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();

        return manager.start();
    }
}
