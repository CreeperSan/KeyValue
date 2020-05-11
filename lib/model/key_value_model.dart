
/// 基类
abstract class BaseKeyValueModel{}

/// 标题
class TitleKeyValueModel extends BaseKeyValueModel{
  String title = '';

  TitleKeyValueModel(this.title);
}

/// 简单的键值对
class SimpleKeyValueModel extends BaseKeyValueModel{
  String key = '';
  String value = '';

  SimpleKeyValueModel(
    this.key,
    this.value,
  );

}

/// 不支持的键值对类型
class UnsupportedKeyValueModel extends BaseKeyValueModel{}
