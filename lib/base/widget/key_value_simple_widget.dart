import 'package:flutter/material.dart';
import 'package:keyvalue/model/key_value_model.dart';

class KeyValueSimpleWidget extends StatefulWidget{
  final double              paddingTop;
  final double              paddingBottom;
  final double              paddingLeft;
  final double              paddingRight;
  final SimpleKeyValueModel model;
  final double              titleTextSize;
  final int                 titleColor;
  final int                 keyWidth;
  final int                 keyColor;
  final int                 valueColor;
  final double              keySize;
  final double              valueSize;

  KeyValueSimpleWidget(
      this.model,{
        this.paddingTop = 16.0,
        this.paddingBottom = 16.0,
        this.paddingLeft = 8.0,
        this.paddingRight = 8.0,
        this.titleColor = 0xff000000,
        this.titleTextSize = 32.0,
        this.keyWidth = 96,
        this.keySize = 16,
        this.keyColor = 0xff000000,
        this.valueSize = 16,
        this.valueColor = 0xff999999,
      });

  @override
  State<StatefulWidget> createState() {
    return _KeyValueSimpleState();
  }

}

class _KeyValueSimpleState extends State<KeyValueSimpleWidget>{

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: widget.paddingTop,
        bottom: widget.paddingBottom,
        left: widget.paddingLeft,
        right: widget.paddingRight,
      ),
      child: Row(
        children: <Widget>[
          Container(
            width: widget.keyWidth.toDouble(),
            child: TextField(
              decoration: InputDecoration(
                  hintText: '此处输入键'
              ),
              controller: TextEditingController(text: widget.model.key),
              onChanged: _onKeyTextChange,
            ),
          ),
          Expanded(
            child: Padding(
              padding: EdgeInsets.only(
                left: 8.0
              ),
              child: TextField(
                decoration: InputDecoration(
                    hintText: '此处输入值'
                ),
                controller: TextEditingController(text: widget.model.value),
                onChanged: _onValueTextChange,
              ),
            ),
          )
        ],
      ),
    );
  }

  void _onKeyTextChange(String value){
    widget.model.key = value;
  }

  void _onValueTextChange(String value){
    widget.model.value = value;
  }

}
