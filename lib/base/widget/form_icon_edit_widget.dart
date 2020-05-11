import 'package:flutter/material.dart';

class FormIconEditWidget extends StatefulWidget{
  final String title;
  final IconData icon;
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final double titleWidth;

  FormIconEditWidget(
    this.title,
    this.icon,{
      this.paddingTop = 4.0,
      this.paddingBottom = 4.0,
      this.paddingLeft = 8.0,
      this.paddingRight = 8.0,
      this.titleWidth = 96.0,
  });

  @override
  State<StatefulWidget> createState() {
    return _FormIconEditState();
  }

}

class _FormIconEditState extends State<FormIconEditWidget>{

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
            width: widget.titleWidth,
            child: Text(widget.title),
          ),
          Expanded(
            child: Container(),
          ),
          Padding(
            padding: EdgeInsets.symmetric(
              horizontal: 8
            ),
            child: Icon(widget.icon),
          ),
          Padding(
            padding: EdgeInsets.symmetric(),
            child: Icon(Icons.chevron_right,
              color: Colors.grey,
              size: 18,
            ),
          )
        ],
      ),
    );
  }

}

