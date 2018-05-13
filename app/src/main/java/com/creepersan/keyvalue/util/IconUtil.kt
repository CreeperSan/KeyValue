package com.creepersan.keyvalue.util

import com.creepersan.keyvalue.R

object IconUtil {
    //　用户按钮
    const val ICON_TABLE_BOARD = 0
    const val ICON_BOOKMARK = 1
    //　保留按钮
    const val ICON_RESERVE_ADD = 0




    fun getIcon(iconID:Int):Int{
        when(iconID){
            ICON_TABLE_BOARD -> return R.drawable.ic_board_table_black_24dp
            ICON_BOOKMARK -> return R.drawable.ic_bookmark_black_24dp
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