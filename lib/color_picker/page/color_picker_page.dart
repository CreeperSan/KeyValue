import 'package:flutter/material.dart';
import 'package:keyvalue/base/utils/format_utils.dart';

class ColorPickerPage extends StatefulWidget{
  int red = 0;
  int green = 0;
  int blue = 0;

  @override
  State<StatefulWidget> createState() {
    return _ColorPickerState();
  }

}

class _ColorPickerState extends State<ColorPickerPage>{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('请选择一个颜色'),
      ),
      body: Column(
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          // 预览色块
          Padding(
            padding: EdgeInsets.symmetric(
              horizontal: 16,
              vertical: 32,
            ),
            child: Container(
              color: Color(0xFF000000 | widget.red << 16 | widget.green << 8 | widget.blue),
              width: MediaQuery.of(context).size.width,
              height: 160,
              child: Center(
                child: Text(FormatUtils.getColorHexString(255, widget.red, widget.green, widget.blue),
                  style: TextStyle(
                    color: Color(0xffFFFFFF),
                    fontSize: 32,
                    shadows: [
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 6,
                        spreadRadius: 4,
                      )
                    ]
                  ),
                ),
              ),
            ),
          ),
          // 颜色
          Text('红'),
          Slider(
            min: 0,
            max: 255,
            value: widget.red.toDouble(),
            onChanged: _onRedChange,
            activeColor: Color(0xFFFF0000),
            inactiveColor: Color(0x33FF0000),
          ),
          Text('绿'),
          Slider(
            min: 0,
            max: 255,
            value: widget.green.toDouble(),
            onChanged: _onGreenChange,
            activeColor: Color(0xFF00FF00),
            inactiveColor: Color(0x3300FF00),
          ),
          Text('蓝'),
          Slider(
            min: 0,
            max: 255,
            value: widget.blue.toDouble(),
            onChanged: _onBlueChange,
            activeColor: Color(0xFF0000FF),
            inactiveColor: Color(0x330000FF),
          ),
          // 按钮
          RaisedButton(
            child: Text('恢复'),
            onPressed: _onRestoreClick,
          ),
          RaisedButton(
            child: Text('确认'),
            onPressed: _onColorConfirmClick,
          ),
        ],
      ),
    );
  }


  ///
  /// 下面是一些时间处理
  ///

  /// 红色被拖动
  void _onRedChange(double red){
    widget.red = red.toInt();
    setState(() {});
  }

  /// 绿色被拖动
  void _onGreenChange(double green){
    widget.green = green.toInt();
    setState(() {});
  }

  /// 蓝色被拖动
  void _onBlueChange(double blue){
    widget.blue = blue.toInt();
    setState(() {});
  }

  void _onColorConfirmClick(){

  }

  void _onRestoreClick(){

  }

}

