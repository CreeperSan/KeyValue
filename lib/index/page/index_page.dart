import 'package:flutter/material.dart';
import 'package:keyvalue/add_table/page/add_table_page.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/utils/navigation_util.dart';
import 'package:keyvalue/index/widget/index_table_widget.dart';
import 'package:keyvalue/model/table_model.dart';
import 'package:keyvalue/base/widget/status_hint_widget.dart';
import 'package:keyvalue/base/utils/database_utils.dart';

class IndexPage extends BaseStatefulPage{
  static const STATE_LOADING  = 0;  // 加载中
  static const STATE_LIST     = 1;  // 加载完成
  static const STATE_ERROR    = 2;  // 加载失败
  static const STATE_EMPTY    = 3;  // 数据为空

  final List<TableModel> tableModelList = [];
  int state = STATE_LOADING;


  @override
  State<IndexPage> createState() {
    return _IndexState();
  }

}

class _IndexState extends State<IndexPage>{

  @override
  void initState(){
    super.initState();
    actionAsync();
  }

  void actionAsync() async {
    try{
      DatabaseUtils databaseUtils = await DatabaseUtils.getInstance();
      List<TableModel> queryList = await databaseUtils.queryAllTables();
      if(queryList.isEmpty){
        widget.state = IndexPage.STATE_EMPTY;
      }else{
        widget.state = IndexPage.STATE_LIST;
      }
    }catch(e){
      widget.state = IndexPage.STATE_ERROR;
      print('-> Exception catched!');
      print(e.toString());
    }
    // 更新界面
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading : false,  // 不显示标题栏的返回按钮
        title: Text(
          '键值对'
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
            itemBuilder: (context, index) => buildTableItemWidget(context, index, widget.tableModelList[index]),
            itemCount: widget.tableModelList.length,
          ),
          // 提示内容
          Offstage(
            offstage: !isCenterHintShow(),
            child: StatusHintWidget(
              hintString: getHintString(),
              showButton: isCenterActionShow(), // 按钮在数据为空或者加载失败时出现
              icon: getHintIcon(),
            ),
          ),
          // 添加按钮
          Offstage(
            offstage: !isAddButtonShow(), // 按钮在数据加载成功是出现
            child: Align(
              alignment: Alignment.bottomRight,
              child: Padding(
                padding: EdgeInsets.only(
                  right: 16,
                  bottom: 16,
                ),
                child: FloatingActionButton(
                  child: Icon(Icons.add),
                  onPressed: _onAddTableClick,
                ),
              ),
            ),
          )
        ],
      ),
    );
  }

  /// 中间提示的图标
  IconData getHintIcon(){
    switch(widget.state){
      case IndexPage.STATE_ERROR:
        return Icons.error_outline;
      case IndexPage.STATE_LOADING:
        return Icons.hourglass_empty;
      case IndexPage.STATE_EMPTY:
        return Icons.turned_in_not;
    }
    return Icons.message;
  }

  /// 中间提示Widget的出现时间
  bool isCenterHintShow(){
    return widget.state != IndexPage.STATE_LIST;
  }

  /// 中间重试按钮的出现时机
  bool isCenterActionShow(){
    return widget.state == IndexPage.STATE_ERROR;
  }

  /// 右下角按钮的出现时机
  bool isAddButtonShow(){
    return widget.state == IndexPage.STATE_LIST || widget.state == IndexPage.STATE_EMPTY;
  }

  /// 位于屏幕中央的提示文本
  String getHintString(){
    switch(widget.state){
      case IndexPage.STATE_ERROR :
        return '记载失败，请稍后重试';
      case IndexPage.STATE_LIST :
        return '';
      case IndexPage.STATE_LOADING :
        return '记载中';
      case IndexPage.STATE_EMPTY:
        return '暂无数据，你可以点击右下键添加按钮添加新的表';
    }
    return '';
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

  /// 按下了添加表
  void _onAddTableClick(){
    NavigationUtil.startStatefulPage(context, AddTablePage()).then((value){
      print(value);
    });
  }

}

