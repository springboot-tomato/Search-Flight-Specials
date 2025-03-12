package com.tomato.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ログイン画面データフォーム
 * 
 * @author ミン
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginForm {
  
  @NotBlank(message = "{0}を入力してください。")
  private String email;
  @NotBlank(message = "{0}を入力してください。")
  private String password;
}
