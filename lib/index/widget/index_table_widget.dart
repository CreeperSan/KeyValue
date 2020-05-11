import 'package:flutter/material.dart';
import 'package:keyvalue/base/widget/base_stateless_widget.dart';

class TableWidget extends BaseStatelessWidget{
  final String _name;                         // 名称
  final IconData _iconData;                   // 图标
  final bool _isPin;                          // 是否需要pin校验
  final bool _isFingerprint;                  // 是否需要指纹校验
  final bool _isCode;                         // 是否需要安全码校验
  final int iconColor;                        // 图标颜色
  final int backgroundColor;                  // 背景颜色
  final void Function() onClick;              // 点击事件
  final void Function() onLongClick;          // 长按事件

  TableWidget(
    this._name,
    this._iconData,
    this._isPin,
    this._isFingerprint,
    this._isCode,{
      this.iconColor = 0xff000000,
      this.backgroundColor = 0xffFFFFFF,
      this.onClick,
      this.onLongClick,
    }
  );

//  @override
//  State<StatefulWidget> createState() {
//    return TableWidgetState();
//  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(
        vertical: 8,
        horizontal: 8,
      ),
      child: GestureDetector(
        onTap: onClick,
        onLongPress: onLongClick,
        child: Container(
          // 边框阴影
          decoration: BoxDecoration(
              color: Color(backgroundColor),
              borderRadius: BorderRadius.circular(6.0),
              boxShadow: [
                BoxShadow(
                    color: Color(0x33000000),
                    offset: Offset(0, 0),
                    blurRadius: 12.0,
                    spreadRadius: 4.0
                )
              ]
          ),
          child: Stack(
            children: <Widget>[
              // 背景图标
              Container(
                child: Align(
                  alignment: Alignment.center,
                  child: Icon(_iconData,
                    size: 64,
                    color: Color(iconColor),
                  ),
                ),
              ),
              // 内容
              Column(
                children: <Widget>[
                  Expanded(
                    child: Container(),
                  ),
                  Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.only(
                        bottomLeft: Radius.circular(6.0),
                        bottomRight: Radius.circular(6.0),
                      ),
                      color: Color(0x50000000),
//                    // 暂时不开启背景图片
//                    image: DecorationImage(
//                      image: NetworkImage('https://www.baidu.com/img/baidu_jgylogo3.gif'),
//                      alignment: Alignment.center,
//                      fit: BoxFit.cover
//                    )
                    ),
                    child: Padding(
                      padding: EdgeInsets.symmetric(
                        vertical: 6,
                        horizontal: 10,
                      ),
                      child: Row(
                        children: <Widget>[
                          Expanded(
                            child: Text(_name,
                              maxLines: 3,
                            ),
                          ),
                          Icon(_iconData)
                        ],
                      ),
                    ),
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }

}

//class TableWidgetState extends BaseStatefulState{
//
//  @override
//  Widget build(BuildContext context) {
//
//    return Padding(
//      padding: EdgeInsets.symmetric(
//        vertical: 8,
//        horizontal: 8,
//      ),
//      child: GestureDetector(
//        onTap: (){},
//        child: Container(
//          // 边框阴影
//          decoration: BoxDecoration(
//              color: Color(0xFFFFFF00),
//              borderRadius: BorderRadius.circular(6.0),
//              boxShadow: [
//                BoxShadow(
//                    color: Color(0x33000000),
//                    offset: Offset(0, 0),
//                    blurRadius: 12.0,
//                    spreadRadius: 4.0
//                )
//              ]
//          ),
//          child: Stack(
//            children: <Widget>[
//              // 背景图标
//              Container(
//                child: Align(
//                  alignment: Alignment.center,
//                  child: Icon(
//                    Icons.ac_unit,
//                    size: 64,
//                    color: Color(0xff66ccff),
//                  ),
//                ),
//              ),
//              // 内容
//              Column(
//                children: <Widget>[
//                  Expanded(
//                    child: Container(),
//                  ),
//                  Container(
//                    decoration: BoxDecoration(
//                      borderRadius: BorderRadius.only(
//                        bottomLeft: Radius.circular(6.0),
//                        bottomRight: Radius.circular(6.0),
//                      ),
//                      color: Color(0x50000000),
////                    // 暂时不开启背景图片
////                    image: DecorationImage(
////                      image: NetworkImage('https://www.baidu.com/img/baidu_jgylogo3.gif'),
////                      alignment: Alignment.center,
////                      fit: BoxFit.cover
////                    )
//                    ),
//                    child: Padding(
//                      padding: EdgeInsets.symmetric(
//                        vertical: 6,
//                        horizontal: 10,
//                      ),
//                      child: Row(
//                        children: <Widget>[
//                          Expanded(
//                            child: Text(
//                              'aaaasdasdasdasdasdsadasd23232323232232323as2d2as32d32sa32d3a2s3d23sa2daasdasasdasd',
//                              maxLines: 3,
//                            ),
//                          ),
//                          Icon(Icons.print)
//                        ],
//                      ),
//                    ),
//                  ),
//                ],
//              )
//            ],
//          ),
//        ),
//      ),
//    );
//  }
//
//}
