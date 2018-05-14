package com.creepersan.keyvalue.util

import com.creepersan.keyvalue.R

object IconUtil {
    //　用户按钮
    const val ICON_TABLE_BOARD = 0
    const val ICON_BOOKMARK = 1
    const val ICON_ANDROID = 2
    const val ICON_MUSIC = 3
    const val ICON_ATTACHMENT = 4
    const val ICON_SETTING = 5
    const val ICON_SHOP = 6
    const val ICON_STAR = 7
    const val ICON_TEXT_SMS = 8
    const val ICON_TOY = 9
    const val ICON_KEY = 10
    const val ICON_WIFI = 11
    const val ICON_AIR_PLANE = 12
    const val ICON_PERSON = 13
    //　保留按钮
    const val ICON_RESERVE_ADD = 0

    // 按钮数组
    val ICON_USER_ARRAY = arrayOf(
            ICON_TABLE_BOARD,
            ICON_BOOKMARK,
            ICON_ANDROID,
            ICON_MUSIC,
            ICON_ATTACHMENT,
            ICON_SETTING,
            ICON_SHOP,
            ICON_STAR,
            ICON_TEXT_SMS,
            ICON_TOY,
            ICON_KEY,
            ICON_WIFI,
            ICON_AIR_PLANE,
            ICON_PERSON
    )





    fun getIcon(iconID:Int):Int{
        when(iconID){
            ICON_TABLE_BOARD -> return R.drawable.ic_board_table_black_24dp
            ICON_BOOKMARK -> return R.drawable.ic_bookmark_black_24dp
            ICON_ANDROID -> return R.drawable.ic_android_black_24dp
            ICON_MUSIC -> return R.drawable.ic_music_black_24dp
            ICON_ATTACHMENT -> return R.drawable.ic_attachment_black_24dp
            ICON_SETTING -> return R.drawable.ic_settings_black_24dp
            ICON_SHOP -> return R.drawable.ic_shop_black_24dp
            ICON_STAR -> return R.drawable.ic_star_black_24dp
            ICON_TEXT_SMS -> return R.drawable.ic_textsms_black_24dp
            ICON_TOY -> return R.drawable.ic_toys_black_24dp
            ICON_KEY -> return R.drawable.ic_vpn_key_black_24dp
            ICON_WIFI -> return R.drawable.ic_wifi_black_24dp
            ICON_AIR_PLANE -> return R.drawable.ic_airplane_black_24dp
            ICON_PERSON -> return R.drawable.ic_person_black_24dp
            else -> return R.drawable.ic_board_table_black_24dp
        }
    }


    /**
     *  获取保留的图标
     */
    fun getReserveIcon(iconID: Int):Int{
        when(iconID){
            ICON_RESERVE_ADD -> return R.drawable.ic_add_black_24dp
        }
        return R.mipmap.ic_launcher
    }

}