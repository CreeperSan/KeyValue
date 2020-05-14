class AuthFlag{
  static const NO_AUTH = 0x00;
  static const CODE = 0x01;
  static const FINGER_PRINT = 0x02;

  bool isUseFingerprint(int auth){
    return auth & CODE != 0;
  }

  bool isUseCode(int auth){
    return auth & CODE != 0;
  }

}