import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/utils/database_utils.dart';
import 'package:keyvalue/base/utils/navigation_util.dart';
import 'package:keyvalue/base/widget/form_single_edit_widget.dart';
import 'package:keyvalue/base/widget/form_icon_edit_widget.dart';
import 'package:keyvalue/base/widget/form_switch_edit_widget.dart';
import 'package:keyvalue/base/widget/form_color_edit_widget.dart';
import 'package:keyvalue/color_picker/page/color_picker_page.dart';
import 'package:keyvalue/icon_picker/page/icon_picker_page.dart';
import 'package:keyvalue/res/const_auth.dart';
import 'package:keyvalue/res/const_icon_id_map.dart';

class AddTablePage extends BaseStatefulPage{
  static const RESULT_SUCCESS = 'success';

  String title = '';
  bool isCreate = false;
  int iconID = IconIDMap.ICON_ADB;
  int iconColor;
  int cardBackgroundColor;

  AddTablePage({
    this.isCreate = true,
    this.title = '',
    this.iconID = IconIDMap.ICON_ADB,
    this.iconColor = 0xff666666,
    this.cardBackgroundColor = 0xffFFFFFF,
  });


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
        title: Text(widget.isCreate ? '创建表' : '编辑表'),
      ),
      body: ListView(
        scrollDirection: Axis.vertical,
        children: <Widget>[
          FormSingleEditWidget(
            '标题',
            widget.title,
            onChange: _onTitleChange,
          ),
          FormIconEditWidget(
            '图标',
            IconIDMap.getIconByID(widget.iconID),
            onClick: _onIconClick,
          ),
          FormColorEditWidget(
            '图标颜色',
            widget.iconColor,
            onClick: _onIconColorClick,
          ),
          FormColorEditWidget(
            '卡片背景颜色',
            widget.cardBackgroundColor,
            onClick: _onCardBackgroundColorClick,
          ),
          RaisedButton(
            child: Text('创建表'),
            onPressed: _onCreateTableClick,
          ),
        ],
      ),
    );
  }


  /// 标题名称改变了
  void _onTitleChange(String value){
    widget.title = value;
  }

  /// 点击了图标选择
  void _onIconClick(){
    NavigationUtil.startStatefulPage(
        context,
        IconPickerPage(
          selectedIconID: widget.iconID,
        )
    ).then((result){
      if(result == null || !result[IconPickerPage.RESULT_SUCCESS]){
        return;
      }
      // 更新图标
      widget.iconID = result[IconPickerPage.RESULT_ICON_ID];
      setState(() {});
    });
  }
  
  /// 点击了图标颜色选择
  void _onIconColorClick(){
    NavigationUtil.startStatefulPage(context, ColorPickerPage.color(widget.iconColor)).then((result){
      if(result == null || !result[ColorPickerPage.RESULT_SUCCESS]){
        return;
      }
      // 设置颜色
      widget.iconColor = result[ColorPickerPage.RESULT_COLOR];
      setState(() {});
    });
  }
  
  /// 点击了卡片背景颜色选择
  void _onCardBackgroundColorClick(){
    NavigationUtil.startStatefulPage(context, ColorPickerPage.color(widget.cardBackgroundColor)).then((result){
      if(result == null || !result[ColorPickerPage.RESULT_SUCCESS]){
        return;
      }
      // 设置颜色
      widget.cardBackgroundColor = result[ColorPickerPage.RESULT_COLOR];
      setState(() {});
    });
  }

  /// 点击了创建表
  void _onCreateTableClick() async {
    if(widget.title.isEmpty){
      print('请输入标题');
      return;
    }
    // 创建表
    DatabaseUtils db = await DatabaseUtils.getInstance();
    db.createTable(
      widget.title,
      AuthFlag.NO_AUTH,
      icon: widget.iconID,
      iconColor: widget.iconColor,
      backgroundColor: widget.cardBackgroundColor,
      description: ''
    ).then((_){
      // 创建成功
      NavigationUtil.finish(context, result: {
        AddTablePage.RESULT_SUCCESS : true
      });
    }, onError: (object){
      // 创建失败
      print('失败！');
    });
  }

}

