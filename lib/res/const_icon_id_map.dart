import 'package:flutter/material.dart';

/// icon 和 数据库中预置图标id 的映射

class IconIDMap{

  static const int ICON_UNKNOWN = 0;
  static const int ICON_4K = 1;
  static const int ICON_AC_UNIT = 2;
  static const int ICON_ALARM = 3;
  static const int ICON_ACCESSIBILITY = 4;
  static const int ICON_ACCOUNT_BALANCE = 5;
  static const int ICON_WALLET = 6;
  static const int ICON_ACCOUNT_CIRCLE = 7;
  static const int ICON_ADB = 8;
  static const int ICON_ADD_BOX = 9;
  static const int ICON_AIRLINE_SEAR_FLAT = 10;
  static const int ICON_PLANT = 11;
  static const int ICON_AIRPORT_SHUTTLE = 12;
  static const int ICON_ALBUM = 13;
  static const int ICON_INFINITY = 14;
  static const int ICON_AT = 15;
  static const int ICON_ANDROID = 16;
  static const int ICON_APPS = 17;
  static const int ICON_ARCHIVE = 18;
  static const int ICON_ARROW_LEFT = 19;
  static const int ICON_ARROW_TOP = 20;
  static const int ICON_ARROW_RIGHT = 21;
  static const int ICON_ARROW_BOTTOM = 22;
  static const int ICON_FLAG = 23;
  static const int ICON_ATM = 24;
  static const int ICON_ATTACH_FILE = 25;
  static const int ICON_ATTACH_FILE_HORIZONTAL = 26;
  static const int ICON_MONEY = 27;
  static const int ICON_MUSIC = 28;
  static const int ICON_AUTO_RENEW = 29;
  static const int ICON_BATTERY = 30;

  static IconData getIconByID(int id){
    return {
      ICON_UNKNOWN : Icons.error_outline,
      ICON_4K : Icons.four_k,
      ICON_AC_UNIT : Icons.ac_unit,
      ICON_ALARM : Icons.alarm,
      ICON_ACCESSIBILITY : Icons.accessibility,
      ICON_ACCOUNT_BALANCE : Icons.account_balance,
      ICON_WALLET : Icons.account_balance_wallet,
      ICON_ACCOUNT_CIRCLE : Icons.account_circle,
      ICON_ADB : Icons.adb,
      ICON_ADD_BOX : Icons.add_box,
      ICON_AIRLINE_SEAR_FLAT : Icons.airline_seat_flat,
      ICON_PLANT : Icons.airplanemode_active,
      ICON_AIRPORT_SHUTTLE : Icons.airport_shuttle,
      ICON_ALBUM : Icons.album,
      ICON_INFINITY : Icons.all_inclusive,
      ICON_AT : Icons.alternate_email,
      ICON_ANDROID : Icons.android,
      ICON_APPS : Icons.apps,
      ICON_ARCHIVE : Icons.archive,
      ICON_ARROW_LEFT : Icons.arrow_back,
      ICON_ARROW_TOP : Icons.arrow_downward,
      ICON_ARROW_RIGHT : Icons.arrow_forward,
      ICON_ARROW_BOTTOM : Icons.arrow_upward,
      ICON_FLAG : Icons.assistant_photo,
      ICON_ATM : Icons.atm,
      ICON_ATTACH_FILE : Icons.attach_file,
      ICON_ATTACH_FILE_HORIZONTAL : Icons.attachment,
      ICON_MONEY : Icons.attach_money,
      ICON_MUSIC : Icons.audiotrack,
      ICON_AUTO_RENEW : Icons.autorenew,
      ICON_BATTERY : Icons.battery_full,
    }[id];
  }


}