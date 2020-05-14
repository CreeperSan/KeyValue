import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/page/base_stateless_page.dart';

class NavigationUtil{

  static Future<dynamic> startStatefulPage(BuildContext context, StatefulWidget page){
    return Navigator.push(
      context,
      MaterialPageRoute(
        builder: (BuildContext context) => page
      )
    );
  }

  static Future<dynamic> startStatelessPage(BuildContext context, StatelessWidget page){
    return Navigator.push(
      context,
      MaterialPageRoute(
        builder: (BuildContext context) => page
      )
    );
  }

  static void finish(BuildContext context, {Object result }){
    Navigator.pop(context, result);
  }

}