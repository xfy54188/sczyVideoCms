/**
 * 初始化toneInfo详情对话框
 */
var ToneinfoInfoDlg = {
    toneinfoInfoData : {}
};

/**
 * 清除数据
 */
ToneinfoInfoDlg.clearData = function() {
    this.toneinfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ToneinfoInfoDlg.set = function(key, val) {
    this.toneinfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ToneinfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ToneinfoInfoDlg.close = function() {
    parent.layer.close(window.parent.Toneinfo.layerIndex);
}

/**
 * 收集数据
 */
ToneinfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('songId')
    .set('songName')
    .set('singerName');
}

/**
 * 提交添加
 */
ToneinfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/toneinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.Toneinfo.table.refresh();
        ToneinfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.toneinfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ToneinfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/toneinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.Toneinfo.table.refresh();
        ToneinfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.toneinfoInfoData);
    ajax.start();
}

$(function() {

});
