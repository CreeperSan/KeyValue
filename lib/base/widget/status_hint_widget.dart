import 'package:flutter/material.dart';
import 'package:keyvalue/base/widget/base_stateful_widget.dart';

class StatusHintWidget extends BaseStatefulWidget{
  String hintString = '';
  String buttonString = '';
  bool showButton = true;
  void Function() onButtonClick;
  IconData icon = Icons.info_outline;

  StatusHintWidget({
    this.hintString = '加载中',
    this.buttonString = '取消',
    this.showButton = true,
    this.icon = Icons.info_outline,
    this.onButtonClick,
  });

  @override
  State<StatusHintWidget> createState() {
    return _StatusHintState();
  }
  
}

class _StatusHintState extends State<StatusHintWidget> with TickerProviderStateMixin{
  AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(duration: Duration(milliseconds: 600), vsync: this);
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(),
            child: RotationTransition(
              turns: _animationController,
              child: Icon(widget.icon,
                size: 82,
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(
              top: 12
            ),
            child: Text(widget.hintString,),
          ),
          Offstage(
            offstage: !widget.showButton,
            child: Padding(
              padding: EdgeInsets.only(
                  top: 12
              ),
              child: RaisedButton(
                child: Text('重新加载'),
                onPressed: (){
                  // 执行动作
                  if(widget.onButtonClick != null){
                    widget.onButtonClick();
                  }
                  _animationController.reset();
                  _animationController.forward();
                },
              ),
            ),
          )
        ],
      ),
    );
  }
  
}
