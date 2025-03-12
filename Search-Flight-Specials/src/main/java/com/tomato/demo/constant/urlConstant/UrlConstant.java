package com.tomato.demo.constant.urlConstant;

/**
 * URL定義クラス
 * 
 * @author ミン
 *
 */
public class UrlConstant {
  /**　ログイン */
  public static final String LOGIN = "/login";
  /**　会員登録 */
  public static final String SIGNUP = "/signup";
  /**　メイン */
  public static final String MAIN = "/";
  /**　認証不要画面 */
  public static final String[] NO_AUTHENCATION = {LOGIN, SIGNUP, MAIN, "/webjars/**", "/js/**"};

  public static final String MYPAGE = "/mypage";
}
