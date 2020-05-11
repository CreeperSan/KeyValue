import 'package:flutter/material.dart';
import 'package:keyvalue/base/widget/status_hint_widget.dart';
import 'package:keyvalue/model/key_value_model.dart';
import 'package:keyvalue/base/widget/key_value_title_widget.dart';
import 'package:keyvalue/base/widget/key_value_simple_widget.dart';
import 'package:keyvalue/base/widget/key_value_unsupport_widget.dart';

class TablePage extends StatefulWidget{
  List<BaseKeyValueModel> dataList = [];


  @override
  State<StatefulWidget> createState() {

    dataList.add(TitleKeyValueModel('这是什么表'));
    dataList.add(SimpleKeyValueModel('这个是键', '这个是值'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('asdasdsa', '45444'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(SimpleKeyValueModel('11', '???'));
    dataList.add(UnsupportedKeyValueModel());

    return _TableState();
  }

}

class _TableState extends State<TablePage>{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('表'),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.delete),
            onPressed: _onTableDeleteClick,
          ),
          IconButton(
            icon: Icon(Icons.info),
            onPressed: _onTableInfoClick,
          ),
        ],
      ),
      body: widget.dataList.isEmpty ? _createEmptyHintWidget() : _createDataListWidget()
    );
  }

  Widget _createEmptyHintWidget(){
    return StatusHintWidget();
  }

  Widget _createDataListWidget(){
    return ListView(
      children: _createContentList(),
    );
  }

  void _onTableDeleteClick(){

  }

  void _onTableInfoClick(){

  }

  List<Widget> _createContentList(){
    List<Widget> widgetList = [];
    for(BaseKeyValueModel model in widget.dataList){
      if(model is TitleKeyValueModel){
        // 如果是标题
        widgetList.add(KeyValueTitleWidget(model));
      } else if (model is SimpleKeyValueModel){
        // 如果是简单的键值对
        widgetList.add(KeyValueSimpleWidget(model));
      } else {
        // 如果是不支持的
        widgetList.add(KeyValueUnsupportWidget());
      }
    }
    return widgetList;
  }

}

