
/// 基类
abstract class BaseKeyValueModel{
  static final int TYPE_UNSUPPORT = -1;
  static final int TYPE_TITLE = 0;
  static final int TYPE_SIMPLE_KEY_VALUE = 1;

  int getType();
}

/// 标题
class TitleKeyValueModel extends BaseKeyValueModel{
  String title = '';

  TitleKeyValueModel(this.title);

  @override
  int getType() {
    return BaseKeyValueModel.TYPE_TITLE;
  }
}

/// 简单的键值对
class SimpleKeyValueModel extends BaseKeyValueModel{
  String key = '';
  String value = '';

  SimpleKeyValueModel(
    this.key,
    this.value,
  );

  @override
  int getType() {
    return BaseKeyValueModel.TYPE_SIMPLE_KEY_VALUE;
  }

}

/// 不支持的键值对类型
class UnsupportedKeyValueModel extends BaseKeyValueModel{

  @override
  int getType() {
    return BaseKeyValueModel.TYPE_UNSUPPORT;
  }

}
