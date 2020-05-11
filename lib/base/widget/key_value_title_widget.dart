import 'package:flutter/material.dart';
import 'package:keyvalue/model/key_value_model.dart';

class KeyValueTitleWidget extends StatefulWidget{
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final TitleKeyValueModel model;
  final double titleTextSize;
  final int    titleColor;

  KeyValueTitleWidget(
    this.model,{
      this.paddingTop = 16.0,
      this.paddingBottom = 16.0,
      this.paddingLeft = 8.0,
      this.paddingRight = 8.0,
      this.titleColor = 0xff000000,
      this.titleTextSize = 32.0
  });

  @override
  State<StatefulWidget> createState() {
    return _KeyValueTitleState();
  }

}

class _KeyValueTitleState extends State<KeyValueTitleWidget>{

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: widget.paddingTop,
        bottom: widget.paddingBottom,
        left: widget.paddingLeft,
        right: widget.paddingRight,
      ),
      child: Text(widget.model.title,
        style: TextStyle(
          color: Color(widget.titleColor),
          fontSize: widget.titleTextSize
        ),
      ),
    );
  }

}
