import 'dart:async';

import 'package:flutter/material.dart';

class TimeUtil{

  static Future delay(int milliseconds) async {
    return Future.delayed(
        Duration(
            milliseconds: milliseconds
        ),
        (){
          print('done');
        }
    );
  }

}
