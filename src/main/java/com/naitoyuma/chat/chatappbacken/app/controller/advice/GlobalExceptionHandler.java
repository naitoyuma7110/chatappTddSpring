package com.naitoyuma.chat.chatappbacken.app.controller.advice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.Data;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  // ResponseEntityはユーザーが定義した例外レスポンスクラスを受け取る
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error");
    // フィールドごとのエラーメッセージをerrorResponseとfieldErrorの内容を追加していく
    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
      errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
    });
    // 作成したerrorResponse、HttpStatus.BAD_REQUEST(ステータスコード)からResponseEntityを作成し返す
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @Data
  static class ErrorResponse {
    private int status;
    private String message;
    private List<FieldError> errors = new ArrayList<>();

    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }

    public void addError(String field, String message) {
      errors.add(new FieldError(field, message));
    }
  }
  @Data
  static class FieldError {
    private String field;
    private String message;

    public FieldError(String field, String message) {
      this.field = field;
      this.message = message;
    }
  }
}
