import 'package:flutter/material.dart';

class TableModel{
  final String name;          // 名称
  final IconData iconData;    // 图标
  final bool isFingerPrint;   // 是否开启指纹校验
  final bool isCode;          // 是否开启安全码校验
  final int iconColor;        // 图标颜色
  final int backgroundColor;  // 背景图片颜色
  final int createTime;       // 创建时间
  final int modifyTime;       // 修改时间

  TableModel(
    this.name,{
      this.iconData = Icons.edit,
      this.isFingerPrint = false,
      this.isCode = false,
      this.iconColor = 0xff000000,
      this.backgroundColor = 0xffFFFFFF,
      this.createTime = 0,
      this.modifyTime = 0,
    }
  );


}