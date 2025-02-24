package br.com.v8.login.model;

public record Email (
    String to,
    String subject,
    String body,
    String contentType){
}
