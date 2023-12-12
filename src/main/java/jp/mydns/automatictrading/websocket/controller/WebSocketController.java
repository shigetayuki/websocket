package jp.mydns.automatictrading.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.mydns.automatictrading.websocket.api.WebsocketApi;

@RestController
public class WebSocketController {
  @Autowired
  private WebsocketApi websocketApi;

  /**
   * トレードを開始するコントローラメソッド
   *
   * @param manageTrade jsonデータ
   * @return 画面情報
   * @throws JsonProcessingException
   */
  @RequestMapping(value = "/start")
  public String start() throws JsonProcessingException {
    // 起動
    websocketApi.start();
    return "{message:起動しました。}";
  }

  /**
   * トレードを終了するコントローラメソッド
   *
   * @param manageTrade jsonデータ
   * @return 画面情報
   * @throws JsonProcessingException
   */
  @RequestMapping(value = "/stop")
  public String stop() throws JsonProcessingException {
    websocketApi.stop();
    return "{message:終了しました。}";
  }

}
