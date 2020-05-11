import 'package:flutter/material.dart';

class KeyValueUnsupportWidget extends StatefulWidget{
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;

  KeyValueUnsupportWidget({
    this.paddingTop = 16.0,
    this.paddingBottom = 16.0,
    this.paddingLeft = 8.0,
    this.paddingRight = 8.0,
  });

  @override
  State<StatefulWidget> createState() {
    return _KeyValueUnsupportStatae();
  }

}

class _KeyValueUnsupportStatae extends State<KeyValueUnsupportWidget>{

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: widget.paddingTop,
        bottom: widget.paddingBottom,
        left: widget.paddingLeft,
        right: widget.paddingRight,
      ),
      child: Text('不支持的类型，请升级你的应用版本'),
    );
  }

}
