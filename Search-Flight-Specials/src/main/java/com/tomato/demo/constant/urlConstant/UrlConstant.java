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
  /**　Amadeusキー */
  public static final String API_GETAMADEUSKEY = "/api/get-amadeusKey";
  public static final String API_SEARCHRESULTS = "/api/search-results";  
  /**　結果Pageー */
  public static final String SEARCHRESULTSPAGE = "/search-results-page";
  /**　認証不要画面 */
  public static final String[] NO_AUTHENCATION = {LOGIN, SIGNUP, MAIN, API_GETAMADEUSKEY, API_SEARCHRESULTS, SEARCHRESULTSPAGE, "/webjars/**", "/js/**"};

  public static final String MYPAGE = "/mypage";
  
}
