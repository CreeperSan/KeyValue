import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/widget/form_single_edit_widget.dart';
import 'package:keyvalue/base/widget/form_icon_edit_widget.dart';
import 'package:keyvalue/base/widget/form_switch_edit_widget.dart';
import 'package:keyvalue/base/widget/form_color_edit_widget.dart';

class AddTablePage extends BaseStatefulPage{
  String title = 'asdasdsad';
  bool isFingerPrint = false;


  @override
  State<StatefulWidget> createState() {
    return AddTableState();
  }

}

class AddTableState extends State<AddTablePage>{

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        leading: Icon(Icons.arrow_back),
        title: Text('创建表'),
      ),
      body: ListView(
        scrollDirection: Axis.vertical,
        children: <Widget>[
          FormSingleEditWidget('标题', widget.title),
          FormIconEditWidget('图标', Icons.access_alarm),
          FormSwitchEditWidget('指纹认证', value: widget.isFingerPrint,),
          FormSwitchEditWidget('安全码认证', value: false,),
          FormColorEditWidget('图标颜色', 0xff000000),
          FormColorEditWidget('卡片背景颜色', 0xffffffff),
          RaisedButton(
            child: Text('创建表'),
            onPressed: (){
              print(widget.title);
            },
          ),
        ],
      ),
    );
  }

}

