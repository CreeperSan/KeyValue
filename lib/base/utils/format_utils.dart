

class FormatUtils{

  // 0 - 15 转Hex字符
  static String toSingleHexString(int num){
    return ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'][num % 16];
  }

  /// 颜色转Hex字符串
  static String getColorHexString(int alpha, int red, int green, int blue){
    return '0x'
        '${toSingleHexString(alpha>>4)}${toSingleHexString(alpha)}'
        '${toSingleHexString(red>>4)}${toSingleHexString(red)}'
        '${toSingleHexString(green>>4)}${toSingleHexString(green)}'
        '${toSingleHexString(blue>>4)}${toSingleHexString(blue)}';
  }

}
