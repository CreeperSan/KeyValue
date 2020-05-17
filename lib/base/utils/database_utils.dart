import 'package:keyvalue/res/const_icon_id_map.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite/sql.dart';
import 'dart:convert';
import 'package:keyvalue/model/table_model.dart';

class DatabaseUtils{

  DatabaseUtils._();

  static DatabaseUtils _instance;

  static Future<DatabaseUtils> getInstance() async {
    if(_instance == null){
      _instance = DatabaseUtils._();
      await _instance.initDatabase();
    }
    return _instance;
  }

  ////////////////////////////////////////////////////////
  Database db;

  Future initDatabase() async {
    // 初始化数据库连接
    db = await openDatabase('key_value.db',
      version: 1,
    );
    // 检查数据库版本并修复
    await databaseVersionCheck();
    // 初始化数据库表
    await initDatabaseTables();
  }

  void databaseVersionCheck() async {
    await db.getVersion();
  }

  void initDatabaseTables() async {
    // 初始化表
    await db.execute('create table if not exists ${ConstDatabaseTable.TABLE_NAME} (' +
        '${ConstDatabaseTable.KEY_ID} integer not null primary key autoincrement,' +
        '${ConstDatabaseTable.KEY_NAME} text not null,' +
        '${ConstDatabaseTable.KEY_AUTH} integer default 0,' +
        '${ConstDatabaseTable.KEY_COLOR} text,' +
        '${ConstDatabaseTable.KEY_CREATE_TIME} datetime default CURRENT_TIMESTAMP,' +
        '${ConstDatabaseTable.KEY_MODIFY_TIME} datetime default CURRENT_TIMESTAMP,' +
        '${ConstDatabaseTable.KEY_INFO} text' +
    ')');
    // 初始化键值对
    await db.execute('create table if not exists ${ConstDatabaseKeyValue.TABLE_NAME} (' +
        '${ConstDatabaseKeyValue.KEY_ID} integer not null primary key autoincrement,' +
        '${ConstDatabaseKeyValue.KEY_TABLE_ID} integer not null,' +
        '${ConstDatabaseKeyValue.KEY_CONTENT} text not null' +
    ')');
  }

  void deInit(){
    db.close();
  }

  Future<void> createTable(
      String name,
      int auth, {
      int icon = IconIDMap.ICON_UNKNOWN,
      int iconColor = 0xff999999,
      int backgroundColor = 0xffFFFFFF,
      String description = '',
  }){
    dynamic colorJson = {
      ConstDatabaseTable.COLOR_ICON : iconColor,
      ConstDatabaseTable.COLOR_BACKGROUND : backgroundColor
    };
    dynamic extraJson = {
      ConstDatabaseTable.EXTRA_DESCRIPTION : description,
      ConstDatabaseTable.EXTRA_ICON : icon,
    };

    return db.execute('insert into ${ConstDatabaseTable.TABLE_NAME} (' +
          '${ConstDatabaseTable.KEY_NAME},'+
          '${ConstDatabaseTable.KEY_AUTH},'+
          '${ConstDatabaseTable.KEY_COLOR},'+
          '${ConstDatabaseTable.KEY_CREATE_TIME},'+
          '${ConstDatabaseTable.KEY_MODIFY_TIME},'+
          '${ConstDatabaseTable.KEY_INFO}' +
        ') values (' +
          '\'$name\','+
          '$auth,'+
          '\'${jsonEncode(colorJson).toString()}\','+
          'strftime(\'%s\',\'now\'),'+
          'strftime(\'%s\',\'now\'),'+
          '\'${jsonEncode(extraJson).toString()}\''+
        ')');
  }

  Future<List<TableModel>> queryAllTables() async {
    List<Map> queryResult = await db.rawQuery('select * from ${ConstDatabaseTable.TABLE_NAME}');
    List<TableModel> returnList = [];
    for(dynamic map in queryResult){
      Map<String, dynamic> colorMap = jsonDecode(map[ConstDatabaseTable.KEY_COLOR]);
      Map<String, dynamic> infoMap = jsonDecode(map[ConstDatabaseTable.KEY_INFO]);
      TableModel model = TableModel(
        map[ConstDatabaseTable.KEY_ID],
        map[ConstDatabaseTable.KEY_NAME],
        icon: infoMap[ConstDatabaseTable.EXTRA_ICON],
        auth: map[ConstDatabaseTable.KEY_AUTH],
        iconColor: colorMap[ConstDatabaseTable.COLOR_ICON],
        backgroundColor: colorMap[ConstDatabaseTable.COLOR_BACKGROUND],
        createTime: map[ConstDatabaseTable.KEY_CREATE_TIME],
        modifyTime: map[ConstDatabaseTable.KEY_MODIFY_TIME],
        description: infoMap[ConstDatabaseTable.EXTRA_DESCRIPTION],
      );
      print(map);
      returnList.add(model);
    }
    return returnList;
  }



  void createKeyValue(
    int tableID,
    String content
  ) async {
    await db.execute('insert into ${ConstDatabaseKeyValue.TABLE_NAME} (' +
      '${ConstDatabaseKeyValue.KEY_TABLE_ID},' +
      '${ConstDatabaseKeyValue.KEY_CONTENT}' +
    ') values {' +
      '$tableID,' +
      '$content' +
    '}');
  }

}

// 一张一张表
class ConstDatabaseTable{
  // _id ID
  // title 名称
  // auth 验证方式
  // color 颜色(JSON格式)
  // create_time 创建时间
  // modify_time 修改时间
  // info 其他信息(JSON格式)

  static const TABLE_NAME = 'table_info';

  static const KEY_ID = '_id';
  static const KEY_NAME = 'name';
  static const KEY_AUTH = 'auth';
  static const KEY_COLOR = 'color';
  static const KEY_CREATE_TIME = 'create_time';
  static const KEY_MODIFY_TIME = 'modify_time';
  static const KEY_INFO = 'info';

  static const COLOR_ICON = 'icon';
  static const COLOR_BACKGROUND = 'bg';

  static const EXTRA_DESCRIPTION = 'description';
  static const EXTRA_ICON = 'icon';
}

// 一条一条数据
class ConstDatabaseKeyValue{
  // _id ID
  // table_id 表的id
  // content 加密后的数据

  static const TABLE_NAME = 'key_value_info';

  static const KEY_ID = '_id';
  static const KEY_TABLE_ID = 'table_id';
  static const KEY_CONTENT = 'content';

}
