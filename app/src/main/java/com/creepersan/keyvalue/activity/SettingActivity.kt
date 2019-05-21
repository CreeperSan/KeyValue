package com.creepersan.keyvalue.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.ViewGroup
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.bean.setting.BaseSettingItem
import com.creepersan.keyvalue.bean.setting.SettingGroupBean
import com.creepersan.keyvalue.bean.setting.SettingKey
import com.creepersan.keyvalue.bean.setting.SettingNormalBean
import com.creepersan.keyvalue.util.DialogBuilder
import com.creepersan.keyvalue.util.FileUtils
import com.creepersan.keyvalue.widget.setting.SettingGroupViewHolder
import com.creepersan.keyvalue.widget.setting.SettingNormalViewHolder
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.ArrayList
import java.util.HashMap

class SettingActivity : BaseActivity() {

    override val layoutID: Int = R.layout.activity_setting

    private val adapter by lazy { SettingListAdapter() }
    private val settingItemList by lazy { ArrayList<BaseSettingItem>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initSettingItem()
        initSettingView()
    }


    private fun initActionBar(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initSettingItem(){
        // Backup Part
        settingItemList.add(SettingGroupBean(getString(R.string.settingGroupBackup)))
        settingItemList.add(SettingNormalBean(R.drawable.ic_import_black_24dp, SettingKey.IMPORT ,getString(R.string.settingItemImport),getString(R.string.settingDescriptionImport)))
        settingItemList.add(SettingNormalBean(R.drawable.ic_export_black_24dp, SettingKey.EXPORT ,getString(R.string.settingItemExport),getString(R.string.settingDescriptionExport)))
        // About Part
    }
    private fun initSettingView(){
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = adapter
    }


    /* Menu */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    /* Inner class */
    private inner class SettingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        /* HERE IS FOR ADAPTER */
        override fun getItemViewType(position: Int): Int {
            return settingItemList[position].type
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when(viewType){
                BaseSettingItem.TYPE_GROUP  -> { return SettingGroupViewHolder(this@SettingActivity, parent) }
                BaseSettingItem.TYPE_NORMAl -> { return SettingNormalViewHolder(this@SettingActivity, parent) }
            }
            return SettingNormalViewHolder(this@SettingActivity, parent)
        }
        override fun getItemCount(): Int {
            return settingItemList.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder.itemViewType){
                BaseSettingItem.TYPE_GROUP  -> { initGroupViewHolder ( holder as SettingGroupViewHolder, settingItemList[position] as SettingGroupBean ) }
                BaseSettingItem.TYPE_NORMAl -> { initNormalViewHolder( holder as SettingNormalViewHolder, settingItemList[position] as SettingNormalBean ) }
            }
        }

        /* HERE IS FOR HOLDER */
        private fun initGroupViewHolder(holder: SettingGroupViewHolder, bean:SettingGroupBean){
            holder.setText(bean.title)
            holder.itemView.setOnClickListener(null)
        }
        private fun initNormalViewHolder(holder: SettingNormalViewHolder, bean:SettingNormalBean){
            holder.iconView.setImageResource(bean.icon)
            holder.titleView.text = bean.title
            holder.descriptionView.text = bean.description
            holder.itemView.setOnClickListener {
                when(bean.key){
                    SettingKey.IMPORT -> {////////////////////////////////////////////////////////// 导入
                        onImportBackupClick()
                    }
                    SettingKey.EXPORT -> {////////////////////////////////////////////////////////// 导出
                        onExportBackupClick()
                    }
                }
            }
        }

    }

    private fun onExportBackupClick(){
        if (!FileUtils.initFileFolder()){
            toast(R.string.settingToastInitFileFolderFail)
            return
        }
        val controller = DialogBuilder.createEditTextDialog(this, "请输入备份文件名称", "", "未命名", "确定", { value -> // 输入备份文件名
            if(FileUtils.isBackupFileExist(value)){
                toast("备份文件已经存在，请使用其他名称")
                return@createEditTextDialog
            }
            val loadingDialogController = DialogBuilder.createLoadingDialog(this, "正在创建备份", "正在检查环境", false)
            loadingDialogController.show()

            val extMap = HashMap<Byte, ByteArray>()
            extMap.put(FileUtils.BACKUP_EXT_VERSION, FileUtils.BACKUP_EXT_VERSION_01)

            FileUtils.writeBackupFile(value, extMap, getTableDao().getAllTable(), getKeyValueDao().getAllKeyValue(), { result, hint -> runOnUiThread {
                toast(hint)
                loadingDialogController.cancel()
            }}, { stepHint ->  runOnUiThread {
                loadingDialogController.setHint(stepHint)
            } })
        })
        controller.show()
    }
    private fun onImportBackupClick(){
        val fileNameList = FileUtils.getBackupFileNamelist()
        if(fileNameList.isEmpty()){
            toast("目录下没有备份文件哦")
            return
        }
        val dialogController = DialogBuilder.createMultiStringListDialog(this, "请选择备份文件", fileNameList) { _, value ->
            if (!FileUtils.isBackupFileExist(value)){
                toast("备份文件不存在")
                return@createMultiStringListDialog
            }
            val progressDialogController = DialogBuilder.createLoadingDialog(this, "正在恢复备份", "准备中...", false)
            progressDialogController.show()
            FileUtils.readBackupFile(value, { _, reason -> runOnUiThread {  // 结果回调
                toast(reason)
                progressDialogController.hide()
            } }, { tableList, keyValueList ->  // 数据回调
                // 清除并写入表
                val tableDao = getTableDao()
                tableDao.getAllTable().forEach {  table ->
                    tableDao.deleteTable(table)
                }
                tableList.forEach { table ->
                    tableDao.insertTable(table)
                }
                // 清除并写入键值对
                val keyValueDao = getKeyValueDao()
                keyValueDao.getAllKeyValue().forEach{ keyValue ->
                    keyValueDao.deleteKeyValue(keyValue)
                }
                keyValueList.forEach { keyValue ->
                    keyValueDao.insertKeyValue(keyValue)
                }
            }, { hint -> runOnUiThread { // 进度回调
                progressDialogController.setHint(hint)
            } })
        }
        dialogController.show()
    }
}