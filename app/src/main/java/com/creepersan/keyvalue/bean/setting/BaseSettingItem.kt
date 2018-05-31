package com.creepersan.keyvalue.bean.setting

abstract class BaseSettingItem {

    companion object {
        const val TYPE_NORMAl = 0
    }

    abstract var title : String
    abstract val type : Int

}