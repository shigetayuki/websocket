package jp.mydns.automatictrading.websocket.api;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@Service
@ClientEndpoint
public class WebsocketApi {
  private Session session;

  @Value("${app.websocketUrl}")
  private String websocketUrl;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public WebsocketApi() {
    super();
  }

  @OnOpen
  public void onOpen(Session session) {
    /* セッション確立時の処理 */
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
    logger.info(sdf.format(new Date()) + " Kabuステーションと接続しました。");
  }

  @OnMessage
  public void onMessage(String message) {
    /* メッセージ受信時の処理 */
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
    logger.info(sdf.format(new Date()) + " Kabuステーションと通信しました。");
    logger.info(sdf.format(new Date()) + message);
    RestApi restApi = new RestApi();
    restApi.post("http://localhost:8080/api/trade", message);
  }

  @OnError
  public void onError(Throwable th) {
    /* エラー発生時の処理 */
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
    logger.error(sdf.format(new Date()) + " Kabuステーションでエラーが発生しました。", th);
  }

  @OnClose
  public void onClose(Session session) {
    /* セッション解放時の処理 */
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
    logger.info(sdf.format(new Date()) + " Kabuステーションと切断しました。");
    try {
      session.close();
      session = null;
    } catch (Exception e) {
      logger.error("システムエラーが発生しました。", e);
    }
  }

  public boolean start() {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    try {
      // サーバー・エンドポイントとのセッションを確立する
      this.session = container.connectToServer(new WebsocketApi(), URI.create(websocketUrl));
      return true;
    } catch (Exception e) {
      logger.error("WebScoket通信でエラーが発生しました。", e);
      return false;
    }
  }

  public boolean stop() {
    if (session == null) {
      return false;
    } else {
      try {
        session.close();
        session = null;
        return true;
      } catch (Exception e) {
        logger.error("WebSocket通信でエラーが発生しました。", e);
        return false;
      }
    }
  }

  public boolean isStart() {
    if (session != null) {
      return true;
    } else {
      return false;
    }
  }
}
