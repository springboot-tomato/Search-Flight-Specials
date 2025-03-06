package com.tomato.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * サインアップ時のデータフォーム
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class SignupForm {
  private String email;
  private String password;
  private String nickname;

}
