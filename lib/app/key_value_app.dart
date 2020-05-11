import 'package:flutter/material.dart';
import 'package:keyvalue/boot/page/boot_page.dart';

class KeyValueApp extends StatelessWidget{

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '键值对',
      theme: ThemeData(
        primaryColor: Colors.blue
      ),
      home: BootPage(),
    );
  }

}
