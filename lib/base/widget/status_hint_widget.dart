import 'package:flutter/material.dart';
import 'package:keyvalue/base/widget/base_stateful_widget.dart';

class StatusHintWidget extends BaseStatefulWidget{
  
  @override
  State<StatefulWidget> createState() {
    return _StatusHintState();
  }
  
}

class _StatusHintState extends BaseStatefulState with TickerProviderStateMixin{
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
              child: Icon(Icons.refresh,
                size: 82,
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(
              top: 12
            ),
            child: Text('加载中',),
          ),
          Padding(
            padding: EdgeInsets.only(
              top: 12
            ),
            child: RaisedButton(
              child: Text('重新加载'),
              onPressed: (){
                print('retry');
                _animationController.reset();
                _animationController.forward();
              },
            ),
          )
        ],
      ),
    );
  }
  
}
