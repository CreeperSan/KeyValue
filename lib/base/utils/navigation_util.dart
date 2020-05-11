import 'package:flutter/material.dart';
import 'package:keyvalue/base/page/base_stateful_page.dart';
import 'package:keyvalue/base/page/base_stateless_page.dart';

class NavigationUtil{

  static void finishCurrentPage(BuildContext context){
    Navigator.pop(context);
  }

  static void toStatefulPage(BuildContext context, StatefulWidget page){
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (BuildContext context) => page
      )
    );
  }

  static void toStatelessPage(BuildContext context, StatelessWidget page){
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (BuildContext context) => page
      )
    );
  }

}