import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/utils/navigation_util.dart';
import 'package:keyvalue/base/utils/time_util.dart';
import 'package:keyvalue/res/const_icon_assets.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:keyvalue/base/utils/config_info_utils.dart';
import 'package:keyvalue/base/utils/database_utils.dart';

import 'package:keyvalue/index/page/index_page.dart';
import 'package:keyvalue/add_table/page/add_table_page.dart';
import 'package:keyvalue/table/page/table_page.dart';
import 'package:keyvalue/color_picker/page/color_picker_page.dart';
import 'package:keyvalue/icon_picker/page/icon_picker_page.dart';

class BootPage extends BaseStatefulPage{

  @override
  State<StatefulWidget> createState() {
    return BootState();
  }

}

class BootState extends BaseState{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.asset(IconRes.APP_KEY_VALUE),
            Text(
              "键值对"
            )
          ],
        ),
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    _toIndexPage();
  }

  void _toIndexPage() async{
    // 初始化启动参数
    await _initBootInfo();
    // 延时2秒
    await TimeUtil.delay(0);
    // 关闭现有界面进入主界面
    NavigationUtil.finish(context);
    NavigationUtil.startStatefulPage(context, IndexPage());
  }


  Future _initBootInfo() async {
    await ConfigInfoUtils.getInstance();
    await DatabaseUtils.getInstance();
  }

}
