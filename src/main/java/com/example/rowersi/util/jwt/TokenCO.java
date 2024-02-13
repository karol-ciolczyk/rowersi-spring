package com.example.rowersi.util.jwt;

import java.io.Serializable;

public class TokenCO implements Serializable {

  private static final long serialVersionUID = -5198455531003510795L;

  private String access_token;
  private int expires_date;
  private String refresh_token;
  private String scope;
  private String token_type;


  public TokenCO() {}

  public TokenCO(String access_token, int expires_date, String refresh_token, String token_type) {
    super();
    this.access_token = access_token;
    this.expires_date = expires_date;
    this.refresh_token = refresh_token;
    this.token_type = token_type;
  }

  public TokenCO(String access_token, int expires_date, String refresh_token, String scope,
      String token_type) {
    super();
    this.access_token = access_token;
    this.expires_date = expires_date;
    this.refresh_token = refresh_token;
    this.scope = scope;
    this.token_type = token_type;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public int getExpires_date() {
    return expires_date;
  }

  public void setExpires_date(int expires_date) {
    this.expires_date = expires_date;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }



}
