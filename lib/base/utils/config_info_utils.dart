import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ConfigInfoUtils{

  ConfigInfoUtils._();

  static ConfigInfoUtils _instance;

  static Future<ConfigInfoUtils> getInstance() async {
    if(_instance == null){
      _instance = ConfigInfoUtils._();
      await _instance.initPref();
    }
    return _instance;
  }

  //////////////////////////////////////////////////////////////////
  static const _PREV_BOOT_VERSION = 'global_prev_boot_version';
  static const _PREV_BOOT_TIMESTAMP = 'global_prev_boot_timestamp';

  SharedPreferences mPref;

  int prevUsedVersion = 0;      // 上次启动的版本
  int prevUsedTimestamp = 0;    // 上次启动的时间戳

  Future initPref() async {
    mPref = await SharedPreferences.getInstance();
    // 读取参数
    prevUsedVersion = mPref.getInt(_PREV_BOOT_VERSION);
    prevUsedTimestamp = mPref.getInt(_PREV_BOOT_TIMESTAMP);
    // 更新参数
    mPref.setInt(_PREV_BOOT_VERSION, 0);
    mPref.setInt(_PREV_BOOT_TIMESTAMP, DateTime.now().millisecondsSinceEpoch);
  }

}
