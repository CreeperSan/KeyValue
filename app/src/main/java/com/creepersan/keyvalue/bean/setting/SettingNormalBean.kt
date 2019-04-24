package com.creepersan.keyvalue.bean.setting

class SettingNormalBean(var icon:Int, var key:Int, override var title: String, var description: String = "") : BaseSettingItem() {
    override val type: Int = BaseSettingItem.TYPE_NORMAl





}