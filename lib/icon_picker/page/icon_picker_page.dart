import 'package:flutter/material.dart';
import 'package:keyvalue/base/utils/navigation_util.dart';
import 'package:keyvalue/res/const_icon_id_map.dart';

class IconPickerPage extends StatefulWidget{
  static const RESULT_SUCCESS = 'success';
  static const RESULT_ICON_ID = 'icon_id';
  
  final List<int> iconDataList = [];
  int selectedIconID = IconIDMap.ICON_UNKNOWN;

  IconPickerPage({
    this.selectedIconID = IconIDMap.ICON_UNKNOWN,
  });

  @override
  State<StatefulWidget> createState() {
    return _IconPickerState();
  }

}

class _IconPickerState extends State<IconPickerPage>{
  SliverGridDelegate _gridDelegate;

  @override
  void initState() {
    super.initState();
    _initIconDataList();
  }

  void _initIconDataList(){
    widget.iconDataList.clear();
    widget.iconDataList.add(IconIDMap.ICON_4K);
    widget.iconDataList.add(IconIDMap.ICON_AC_UNIT);
    widget.iconDataList.add(IconIDMap.ICON_ALARM);
    widget.iconDataList.add(IconIDMap.ICON_ACCESSIBILITY);
    widget.iconDataList.add(IconIDMap.ICON_ACCOUNT_BALANCE);
    widget.iconDataList.add(IconIDMap.ICON_WALLET);
    widget.iconDataList.add(IconIDMap.ICON_ACCOUNT_CIRCLE);
    widget.iconDataList.add(IconIDMap.ICON_ADB);
    widget.iconDataList.add(IconIDMap.ICON_ADD_BOX);
    widget.iconDataList.add(IconIDMap.ICON_AIRLINE_SEAR_FLAT);
    widget.iconDataList.add(IconIDMap.ICON_PLANT);
    widget.iconDataList.add(IconIDMap.ICON_AIRPORT_SHUTTLE);
    widget.iconDataList.add(IconIDMap.ICON_ALBUM);
    widget.iconDataList.add(IconIDMap.ICON_INFINITY);
    widget.iconDataList.add(IconIDMap.ICON_AT);
    widget.iconDataList.add(IconIDMap.ICON_ANDROID);
    widget.iconDataList.add(IconIDMap.ICON_APPS);
    widget.iconDataList.add(IconIDMap.ICON_ARCHIVE);
    widget.iconDataList.add(IconIDMap.ICON_ARROW_LEFT);
    widget.iconDataList.add(IconIDMap.ICON_ARROW_TOP);
    widget.iconDataList.add(IconIDMap.ICON_ARROW_RIGHT);
    widget.iconDataList.add(IconIDMap.ICON_ARROW_BOTTOM);
    widget.iconDataList.add(IconIDMap.ICON_FLAG);
    widget.iconDataList.add(IconIDMap.ICON_ATM);
    widget.iconDataList.add(IconIDMap.ICON_ATTACH_FILE);
    widget.iconDataList.add(IconIDMap.ICON_ATTACH_FILE_HORIZONTAL);
    widget.iconDataList.add(IconIDMap.ICON_MONEY);
    widget.iconDataList.add(IconIDMap.ICON_MUSIC);
    widget.iconDataList.add(IconIDMap.ICON_AUTO_RENEW);
    widget.iconDataList.add(IconIDMap.ICON_BATTERY);

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('请选择一个图标'),
        actions: <Widget>[
          FlatButton(
            child: Text('确定',
              style: TextStyle(
                color: Colors.white
              ),
            ),
            onPressed: _onIconConfirm,
          )
        ],
      ),
      body: GridView.builder(
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 5,
        ),
        itemBuilder: (BuildContext context, int position){
          return Center(
            child: IconButton(
              color: widget.selectedIconID == widget.iconDataList[position] ? Colors.blueAccent : Colors.grey,
              icon: Icon(IconIDMap.getIconByID(widget.iconDataList[position]),
                size: 32,
              ),
              onPressed: () => _onIconPressed(position),
            ),
          );
        },
        itemCount: widget.iconDataList.length,
      ),
    );
  }

  void _onIconPressed(int position){
    widget.selectedIconID = widget.iconDataList[position];
    setState(() {});
  }

  void _onIconConfirm(){
    NavigationUtil.finish(context, result : {
      IconPickerPage.RESULT_SUCCESS : true,
      IconPickerPage.RESULT_ICON_ID : widget.selectedIconID,
    });
  }

}

