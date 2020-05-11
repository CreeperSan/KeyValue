import 'package:sqflite/sqflite.dart';
import 'package:sqflite/sql.dart';

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
    // TODO 尚未完成
//    await db.execute('CREATE TABLE ${ConstDatabaseKeyValue.TABLE_NAME} IF NOT EXISTS('
//        ''
//    ')');
  }

  void deInit(){
    db.close();
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

  static const TABLE_NAME = 'table';

  static const KEY_ID = '_id';
  static const KEY_TITLE = 'title';
  static const KEY_AUTH = 'auth';
  static const KEY_COLOR = 'color';
  static const KEY_CREATE_TIME = 'create_time';
  static const KEY_MODIFY_TIME = 'modify_time';
  static const KEY_INFO = 'info';
}

// 一条一条数据
class ConstDatabaseKeyValue{
  // _id ID
  // table_id 表的id
  // content 加密后的数据

  static const TABLE_NAME = 'key_value';

  static const KEY_ID = '_id';
  static const KEY_TABLE_ID = 'table_id';
  static const KEY_CONTENT = 'content';

}
