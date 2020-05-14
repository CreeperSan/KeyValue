import 'package:flutter/material.dart';
import 'package:keyvalue/base/utils/format_utils.dart';
import 'package:keyvalue/base/utils/navigation_util.dart';

class ColorPickerPage extends StatefulWidget{
  static const RESULT_SUCCESS = 'success';
  static const RESULT_COLOR   = 'color';
  static const RESULT_ALPHA   = 'alpha';
  static const RESULT_RED     = 'red';
  static const RESULT_GREEN   = 'green';
  static const RESULT_BLUE    = 'blue';

  int alpha = 0;
  int red = 0;
  int green = 0;
  int blue = 0;

  /// 没有预设颜色
  ColorPickerPage(){
    alpha = 255;
    red = 0;
    green = 0;
    blue = 0;
  }

  // 预设颜色int
  ColorPickerPage.color(int color){
    this.alpha = (color & 0xFF000000) >> (8 * 3);
    this.red   = (color & 0x00FF0000) >> (8 * 2);
    this.green = (color & 0x0000FF00) >> (8 * 1);
    this.blue  = (color & 0x000000FF) >> (8 * 0);
  }

  /// 预设RGB和透明度
  ColorPickerPage.argb(
      this.red,
      this.green,
      this.blue,
      this.alpha
  );

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
      body: SingleChildScrollView(
        child: Column(
          mainAxisSize: MainAxisSize.max,
          children: <Widget>[
            // 预览色块
            Padding(
              padding: EdgeInsets.symmetric(
                horizontal: 16,
                vertical: 32,
              ),
              child: Container(
                decoration: BoxDecoration(
                  boxShadow: [
                    BoxShadow(
                      color: Color(0x33999999),
                      spreadRadius: 12,
                      blurRadius: 12,
                    )
                  ],
                  color: Color(0xFF000000 | widget.red << 16 | widget.green << 8 | widget.blue),
                  borderRadius: BorderRadius.circular(6.0),
                ),
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
              child: Text('确认'),
              onPressed: _onColorConfirmClick,
            ),
          ],
        ),
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
    NavigationUtil.finish(context, result : {
      ColorPickerPage.RESULT_SUCCESS : true,
      ColorPickerPage.RESULT_ALPHA : widget.alpha,
      ColorPickerPage.RESULT_RED : widget.red,
      ColorPickerPage.RESULT_GREEN : widget.green,
      ColorPickerPage.RESULT_BLUE : widget.blue,
      ColorPickerPage.RESULT_COLOR : 0x00000000 |
                                      (widget.alpha << 8 * 3) |
                                      (widget.red   << 8 * 2) |
                                      (widget.green << 8 * 1) |
                                      (widget.blue  << 8 * 0)
    });
  }

}

