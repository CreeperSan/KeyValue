package com.creepersan.keyvalue.database

object DBKey {

    const val NAME_DATABASE = "KeyValue.db"

    const val TABLE_TABLE = "CollectionTable"
    const val KEY_TABLE_ID = "id"
    const val KEY_TABLE_NAME = "collection_name"
    const val KEY_TABLE_TYPE = "type"
    const val KEY_TABLE_ICON = "icon"
    const val KEY_TABLE_DESCRIPTION = "description"
    const val KEY_TABLE_CREATE_TIME = "create_time"

    const val TABLE_KEY_VALUE_PAIR = "KeyValueTable"
    const val KEY_KEY_VALUE_PAIR_ID = "id"
    const val KEY_KEY_VALUE_PAIR_NAME = "key"
    const val KEY_KEY_VALUE_PAIR_TYPE = "type"
    const val KEY_KEY_VALUE_PAIR_ICON = "icon"
    const val KEY_KEY_VALUE_PAIR_VALUE = "value"
    const val KEY_KEY_VALUE_PAIR_DESCRIPTION = "description"
    const val KEY_KEY_VALUE_PAIR_TABLE = "table_id"
    const val KEY_KEY_VALUE_PAIR_CREATE_TIME = "create_time"

}