package com.tomato.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ログイン時のデータフォーム
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginForm {
  private String loginId;
  private String password;
}
