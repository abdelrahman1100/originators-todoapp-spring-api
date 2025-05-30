package com.masteryhub.todoapp.models.settingsModel;

public enum ThemeColor {
  RED("#FF0000"),
  GREEN("#00FF00"),
  BLUE("#0000FF"),
  YELLOW("#FFFF00"),
  ORANGE("#FFA500"),
  PURPLE("#800080"),
  PINK("#FFC0CB"),
  BROWN("#A52A2A"),
  BLACK("#000000"),
  WHITE("#FFFFFF");

  private final String hexValue;

  ThemeColor(String hexValue) {
    this.hexValue = hexValue;
  }

  public String getHexValue() {
    return hexValue;
  }
}
