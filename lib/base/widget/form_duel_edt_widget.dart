import 'package:flutter/material.dart';

class FormDuelEditWidget extends StatefulWidget{
  final int index;
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final String firstText;
  final String firstEditHint;
  final double firstEditWidth;
  final int firstEditColor;
  final int firstEditHintColor;
  final void Function(String value) firstEditOnChange;
  final String secondText;
  final String secondEditHint;
  final int secondEditColor;
  final int secondEditHintColor;
  final void Function(String value) secondEditOnChange;

  FormDuelEditWidget({
    this.index = -1,
    this.paddingTop = 6,
    this.paddingBottom = 6,
    this.paddingLeft = 12,
    this.paddingRight = 12,
    this.firstText = '',
    this.firstEditHint = '请输入文本',
    this.firstEditWidth = 96,
    this.firstEditColor = 0xff000000,
    this.firstEditHintColor = 0xffaaaaaa,
    this.firstEditOnChange,
    this.secondText = '',
    this.secondEditHint = '请输入文本',
    this.secondEditColor = 0xff000000,
    this.secondEditHintColor = 0xffaaaaaa,
    this.secondEditOnChange,
  });


  @override
  State<StatefulWidget> createState() {
    return FormDuelEditState();
  }

}

class FormDuelEditState extends State<FormDuelEditWidget>{

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
          Offstage(
            offstage: widget.index < 0,
            child: Container(
              width: 42,
              padding: EdgeInsets.symmetric(
                horizontal: 6
              ),
              child: Text(widget.index.toString()),
            ),
          ),
          Container(
            width: widget.firstEditWidth,
            child: TextField(
              controller: TextEditingController(text: widget.firstText),
              decoration: InputDecoration(
                hintText: widget.firstEditHint,
                hintStyle: TextStyle(
                    color: Color(widget.firstEditHintColor)
                ),
              ),
              onChanged: (value){
                if(widget.firstEditOnChange != null){
                  widget.firstEditOnChange(value);
                }
              },
            ),
          ),
          Expanded(
            child: Padding(
              padding: EdgeInsets.only(
                left: 8
              ),
              child: TextField(
                controller: TextEditingController(text: widget.secondText),
                decoration: InputDecoration(
                  hintText: widget.secondEditHint,
                  hintStyle: TextStyle(
                    color: Color(widget.secondEditHintColor)
                  ),
                ),
                onChanged: (value){
                  if(widget.secondEditOnChange != null){
                    widget.secondEditOnChange(value);
                  }
                },
              ),
            ),
          )
        ],
      ),
    );
  }

}

