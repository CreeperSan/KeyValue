import 'package:flutter/material.dart';

class FormSwitchEditWidget extends StatefulWidget{
  final String title;
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final double titleWidth;
  final bool value;
  final void Function(bool) onChange;

  FormSwitchEditWidget(
    this.title, {
      this.value = false,
      this.paddingTop = 4.0,
      this.paddingBottom = 4.0,
      this.paddingLeft = 8.0,
      this.paddingRight = 8.0,
      this.titleWidth = 96.0,
      this.onChange,
  });

  @override
  State<StatefulWidget> createState() {
    return _FormSwitchEditState();
  }


}

class _FormSwitchEditState extends State<FormSwitchEditWidget>{

  @override
  Widget build(BuildContext context) {
    print('build->${widget.value}');

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
            width: widget.titleWidth,
            child: Text(widget.title,),
          ),
          Expanded(
            child: Container(),
          ),
          Switch(
            activeColor: Colors.blue,
            value: widget.value,
            onChanged: widget.onChange,
          ),
        ],
      ),
    );
  }

}

