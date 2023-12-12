package jp.mydns.automatictrading.websocket.constant;

/**
 * APIを管理する定数クラス
 */
public class API_CONST {
  /** 市場コード(コードID) */
  public static final String EXCHANGE = "1";
  /** 日通し */
  public static final Integer EXCHANGE_ALL = 2;
  /** 取引区分（コードID） */
  public static final String TRADE_TYPE = "2";
  /** 取引区分：新規 */
  public static final Integer TRADE_TYPE_NEW = 1;
  /** 取引区分：決済 */
  public static final Integer TRADE_TYPE_SETTLEMENT = 2;
  /** 有効期間条件（コードID） */
  public static final String TIME_IN_FORCE = "3";
  /** FAK */
  public static final Integer TIME_IN_FORCE_FAK = 2;
  /** 売買区分（コードID） */
  public static final String SIDE = "4";
  /** 売買区分：売 */
  public static final String SIDE_SEL = "1";
  /** 売買区分：買 */
  public static final String SIDE_BUY = "2";
  /** 執行条件（コードID） */
  public static final String FRONT_ORDER_TYPE = "5";
  /** 執行条件：成行 */
  public static final Integer FRONT_ORDER_TYPE_MARKET = 120;
  /** 先物銘柄コード（コードID） */
  public static final String FURURE_CODE = "6";
  /** 先物銘柄コード：日経２２５ラージ */
  public static final String FURURE_CODE_NIKKE225 = "NK225";
  /** 先物銘柄コード：日経２２５ミニ */
  public static final String FURURE_CODE_NIKKE225_MINI = "NK225mini";
  /** コンテンツタイプ */
  public static final String CONTENT_TYPE = "application/json; charset=utf-8";
}
