import 'package:flutter/material.dart';
import 'package:keyvalue/model/key_value_model.dart';
import 'package:keyvalue/model/table_model.dart';
import 'package:keyvalue/res/const_icon_id_map.dart';

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
      ),
      body: Stack(
        children: <Widget>[
//          ListView.builder(itemBuilder: null),
          SingleChildScrollView(
            child: Column(
              children: buildBodyWidgetList(),
            ),
          ),
          Positioned(
            right: 16.0,
            bottom: 16.0,
            child: FloatingActionButton(
              child: Icon(
                Icons.add,
                color: Colors.white,
              ),
              onPressed: _onAddKeyValueClicked,
            ),
          )
        ],
      ),
    );
  }

  /// 生成列表内容
  List<Widget> buildBodyWidgetList(){
    List<Widget> bodyWidgetList = [];
    bodyWidgetList.add(buildBodyWidgetListHeader());    // 头部
    return bodyWidgetList;
  }

  /// 生成列表头部Widget
  Widget buildBodyWidgetListHeader(){
    return Container(
      height: 92,
      decoration: BoxDecoration(
        color: Color(widget.tableModel.backgroundColor),
        boxShadow: [
          BoxShadow(
            color: Color(0xff000000),
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
    );
  }

  void _onAddKeyValueClicked(){

  }

}
