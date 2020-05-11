import 'package:flutter/material.dart';

class FormColorEditWidget extends StatefulWidget{
  final String title;
  final int color;
  final double paddtingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final double titleWidth;

  FormColorEditWidget(
    this.title,
    this.color,{
      this.paddtingTop = 8.0,
      this.paddingBottom = 8.0,
      this.paddingLeft = 8.0,
      this.paddingRight = 8.0,
      this.titleWidth = 96.0
  });

  @override
  State<StatefulWidget> createState() {
    return _FormColorEditState();
  }

}

class _FormColorEditState extends State<FormColorEditWidget>{

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        top: widget.paddtingTop,
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
            child: Container(
              width: 24,
              height: 24,
              decoration: BoxDecoration(
                color: Color(widget.color),
                boxShadow: [
                  BoxShadow(
                    color: Color(0x66999999),
                    blurRadius: 6.0,
                    spreadRadius: 6.0,
                    offset: Offset(0, 0),
                  )
                ]
              ),
            ),
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

