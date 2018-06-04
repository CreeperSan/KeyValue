package com.creepersan.keyvalue.bean.setting

class SettingGroupBean(override var title: String) : BaseSettingItem(){
    override val type: Int = BaseSettingItem.TYPE_GROUP
}