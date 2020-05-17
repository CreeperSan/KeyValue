import 'package:flutter/material.dart';
import 'package:keyvalue/res/const_icon_id_map.dart';

class TableModel{
  final int id;               // 数据库中的id
  final String name;          // 名称
  final int icon;             // 图标
  final int auth;             // 安全校验方式
  final int iconColor;        // 图标颜色
  final int backgroundColor;  // 背景图片颜色
  final int createTime;       // 创建时间
  final int modifyTime;       // 修改时间
  final String description;   // 描述

  TableModel(
    this.id,
    this.name,{
      this.icon = IconIDMap.ICON_UNKNOWN,
      this.auth = 0,
      this.iconColor = 0xff000000,
      this.backgroundColor = 0xffFFFFFF,
      this.createTime = 0,
      this.modifyTime = 0,
      this.description = '',
    }
  );


}