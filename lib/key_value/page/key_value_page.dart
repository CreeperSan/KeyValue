import 'package:flutter/material.dart';
import 'package:keyvalue/base/utils/database_utils.dart';
import 'package:keyvalue/base/utils/widget_utils.dart';
import 'package:keyvalue/base/widget/form_duel_edt_widget.dart';
import 'package:keyvalue/model/key_value_model.dart';
import 'package:keyvalue/model/table_model.dart';
import 'package:keyvalue/res/const_icon_id_map.dart';
import 'dart:convert';

class KeyValuePage extends StatefulWidget{
  final TableModel tableModel;
  final List<BaseKeyValueModel> keyValueList = [];

  KeyValuePage(this.tableModel){
    if(tableModel == null){
      throw Exception('tableModel can not be null!');
    }
  }


  @override
  State<StatefulWidget> createState() {
    return _KeyValueState();
  }

}

class _KeyValueState extends State<KeyValuePage>{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.tableModel.name),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.save,),
            onPressed: _onSaveClicked,
          ),
        ],
      ),
      body: Stack(
        children: <Widget>[
          ListView.builder(
            itemBuilder: _getItemWidget,
            itemCount: _getItemCount(),
          ),
        ],
      ),
    );
  }

  
  
  /// 列表内容
  Widget _getItemWidget(BuildContext context, int position){
    if(position <= 0){
      return _buildListHeaderWidget();
    }else if(position >= 1 + widget.keyValueList.length){
      return _buildListFooterWidget();
    }else{
      return _buildListItemWidget(context, position);
    }
  }

  /// 列表数据
  int _getItemCount(){
    // 头部 + 列表数据 + 添加新数据
    return 1 + widget.keyValueList.length + 1;
  }

  /// 生成列表的Widget
  Widget _buildListItemWidget(BuildContext context, int position){
    int listPosition = position - 1;
    BaseKeyValueModel model = widget.keyValueList[listPosition];
    if(model is SimpleKeyValueModel){
      return _buildSimpleKeyValueItemWidget(context, model, position, listPosition);
    }else{
      return _buildUnsuportedtItemWidget();
    }
  }

  /// 生成简单的键值对 Widget
  Widget _buildSimpleKeyValueItemWidget(BuildContext context, SimpleKeyValueModel model, int position, int listPosition){
    return FormDuelEditWidget(
      index: listPosition + 1,
      firstText: model.key,
      firstEditHint: '请输入键',
      firstEditOnChange: (value){
        model.key = value;
      },
      secondText: model.value,
      secondEditHint: '请输入值',
      secondEditOnChange: (value){
        model.value = value;
      },
    );
  }

  /// 生成不支持的键值对 Widget
  Widget _buildUnsuportedtItemWidget(){
    return Center(
      child: Text(
        '不支持的信息，请升级应用版本',
        style: TextStyle(
          color: Colors.grey,
        ),
      ),
    );
  }

  /// 生成底部的提示
  Widget _buildListFooterWidget(){
    return InkWell(
      child: ListTile(
        leading: Icon(
          Icons.add,
          color: Colors.grey,
        ),
        title: Text(
          '添加键值对',
          style: TextStyle(
            color: Colors.grey,
          ),
        ),
      ),
      onTap: _onAddKeyValueClicked,
    );
  }

  /// 生成列表头部Widget
  Widget _buildListHeaderWidget(){
    return Padding(
      padding: EdgeInsets.only(
        bottom: 6
      ),
      child: Container(
        height: 92,
        decoration: BoxDecoration(
          color: Color(widget.tableModel.backgroundColor),
          boxShadow: [
            BoxShadow(
              color: Color(0x11000000),
              blurRadius: 6.0,
              spreadRadius: 6.0,
            )
          ],
        ),
        child: Padding(
          padding: EdgeInsets.symmetric(
            horizontal: 12.0,
          ),
          child: Row(
            children: <Widget>[
              Icon(
                IconIDMap.getIconByID(widget.tableModel.icon),
                size: 64,
                color: Color(widget.tableModel.iconColor),
              ),
              Padding(
                padding: EdgeInsets.only(
                    left: 12.0
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      widget.tableModel.name,
                      style: TextStyle(
                        color: Color(widget.tableModel.iconColor),
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Text(
                      widget.tableModel.description.isEmpty ? '无简介' : widget.tableModel.description,
                      style: TextStyle(
                          color: Color(widget.tableModel.iconColor)
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  /// 添加键值对按钮点击事件
  void _onAddKeyValueClicked(){
    widget.keyValueList.add(SimpleKeyValueModel('', ''));
    setState(() {});
  }

  /// 保存点击事件
  void _onSaveClicked() async {
    // 检查是否有数据
    if(widget.keyValueList.isEmpty){
      WidgetUtils.showSimpleDialogHint(context, '请先添加数据！');
      return;
    }
    // 检查是否都有设置值
    for(int index=0; index<widget.keyValueList.length; index++ ){
      BaseKeyValueModel baseModel = widget.keyValueList[index];
      if(baseModel is SimpleKeyValueModel){
        // 如果是双值的键值对
        if(baseModel.key.trim().isEmpty){
          WidgetUtils.showSimpleDialogHint(context, '第 ${index+1} 条数据的键为空，请先输入内容');
          return;
        }
        if(baseModel.value.trim().isEmpty){
          WidgetUtils.showSimpleDialogHint(context, '第 ${index+1} 条数据的值为空，请先输入内容');
          return;
        }
      }else if(baseModel is UnsupportedKeyValueModel){
        WidgetUtils.showSimpleDialogHint(context, '第 ${index+1} 条数据位置，为了数据安全，请先升级应用版本');
        return;
      }
    }
    // 组合数据
    List databaseItemInsert = [];
    for(BaseKeyValueModel baseModel in widget.keyValueList){
      if(baseModel is SimpleKeyValueModel){
        databaseItemInsert.add({
          'type' : baseModel.getType(),
          'key' : baseModel.key,
          'value' : baseModel.value,
        });
      }
    }
    // 检查数据通过，保存或插入数据
    DatabaseUtils databaseUtils = await DatabaseUtils.getInstance();
    databaseUtils.createKeyValue(widget.tableModel.id, jsonEncode(databaseItemInsert));
    WidgetUtils.showSimpleDialogHint(context, '成功！');
  }

}
