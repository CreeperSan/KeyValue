import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/index/widget/index_table_widget.dart';
import 'package:keyvalue/model/table_model.dart';
import 'package:keyvalue/base/widget/status_hint_widget.dart';

class IndexPage extends BaseStatefulPage{

  @override
  State<StatefulWidget> createState() {
    return _IndexState();
  }

}

class _IndexState extends BaseState{
  final List<TableModel> _tableModelList = [
    TableModel(''),
    TableModel(''),
    TableModel(''),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          '键值对'
        ),
        leading: IconButton(
          icon: Icon(
            Icons.menu
          ),
          onPressed: _onMenuClick,
        ),
      ),
      body: Stack(
        children: <Widget>[
          // 列表
          GridView.builder(
            scrollDirection: Axis.vertical,
            gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
              //单个子Widget的水平最大宽度
              maxCrossAxisExtent: MediaQuery.of(context).size.width / 2,
            ),
            itemBuilder: (context, index) => buildTableItemWidget(context, index, _tableModelList[index]),
            itemCount: _tableModelList.length,
          ),
          // 提示内容
          StatusHintWidget(),
          // 添加按钮
          Positioned(
            right: 16,
            bottom: 16,
            child: FloatingActionButton(
              child: Icon(Icons.add),
              onPressed: _onAddTableClick,
            ),
          ),
        ],
      ),
    );
  }

  /// 生成 Item 的 Widget
  TableWidget buildTableItemWidget(BuildContext context, int index, TableModel model){
    return TableWidget(
      '未命名 $index',
      Icons.print,
      true,
      true,
      true,
      iconColor: 0xff000000,
      backgroundColor: 0xffFFFFFF,
      onClick: () => print('点击了 $index'),
      onLongClick: () => print('长按了 $index'),
    );
  }

  ///  标题菜单按钮点击事件
  void _onMenuClick(){
    print('click');
  }

  /// 按下了添加表
  void _onAddTableClick(){
    print('点击了 添加表');
  }

}

