package com.nuvola.myproject.client.application;

import javax.inject.Inject;

import org.realityforge.gwt.websockets.client.WebSocket;
import org.realityforge.gwt.websockets.client.WebSocketListenerAdapter;

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.nuvola.myproject.client.NameTokens;
import com.nuvola.myproject.client.application.ApplicationPresenter.MyProxy;
import com.nuvola.myproject.client.application.ApplicationPresenter.MyView;
import com.nuvola.myproject.shared.ResourcePaths;

public class ApplicationPresenter extends Presenter<MyView, MyProxy> {
    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }


    interface MyView extends View {
        void appendText(String text);
    }

    private WebSocket webSocket;
    private Timer timer;

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);

        timer = new Timer() {
            @Override
            public void run() {
                webSocket.send("Hey it is the seconds time...");
            }
        };
    }

    @Override
    protected void onReveal() {
        webSocket = WebSocket.newWebSocketIfSupported();
        webSocket.setListener(new WebSocketListenerAdapter() {
            @Override
            public void onMessage(WebSocket webSocket, String s) {
                getView().appendText(s);
            }
        });

        webSocket.connect("ws://localhost:8080/api" + ResourcePaths.QUOTES);
        timer.scheduleRepeating(500);
    }
}
