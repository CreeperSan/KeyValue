import 'package:flutter/material.dart';
import 'package:keyvalue/base/widget/base_stateful_widget.dart';

class FormSingleEditWidget extends StatefulWidget{
  final double paddingTop;
  final double paddingBottom;
  final double paddingLeft;
  final double paddingRight;
  final String title;
  final double titleWidth;
  final int titleColor;
  final String content;
  final String hint;
  final void Function(String value) onChange;

  FormSingleEditWidget(
    this.title,
    this.content,{
      this.paddingTop = 4.0,
      this.paddingBottom = 4.0,
      this.paddingLeft = 8.0,
      this.paddingRight = 8.0,
      this.titleWidth = 96.0,
      this.titleColor = 0xff000000,
      this.hint = '',
      this.onChange,
  });


  @override
  FormSingleEditState createState() {
    return FormSingleEditState();
  }

}

class FormSingleEditState extends State<FormSingleEditWidget>{

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
            child: Text(widget.title,
              style: TextStyle(
                color: Color(widget.titleColor)
              ),
            ),
          ),
          Expanded(
            child: TextField(
              decoration: InputDecoration(
                hintText: widget.content
              ),
              controller: TextEditingController(text: widget.content),
              onChanged: (newContent){
                if(widget.onChange != null){
                  widget.onChange(newContent);
                }
              },
            ),
          ),
        ],
      ),
    );
  }

}
