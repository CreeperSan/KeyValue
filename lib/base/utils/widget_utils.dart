import 'package:flutter/material.dart';

class WidgetUtils{
  
  static Future<dynamic> showSimpleDialogHint(BuildContext context, String message,{
    bool outsideTouchDismissible = true,
    String title = '',
    String positiveButtonText = '',
    void Function() positiveButtonAction,
    String negativeButtonText = '',
    void Function() negativeButtonAction,
  }){
    return showDialog(
      context: context,
      barrierDismissible: outsideTouchDismissible,
      builder: (BuildContext context){
        return AlertDialog(
          title: title.isEmpty ? null : Text(title),
          content: Text(message),
          actions: <Widget>[
            Offstage(
              offstage: positiveButtonText.isEmpty,
              child: FlatButton(
                child: Text(positiveButtonText),
                onPressed: positiveButtonAction,
              ),
            ),
            Offstage(
              offstage: negativeButtonText.isEmpty,
              child: FlatButton(
                child: Text(negativeButtonText),
                onPressed: negativeButtonAction,
              ),
            ),
          ],
        );
      }
    );
  }
  
}
