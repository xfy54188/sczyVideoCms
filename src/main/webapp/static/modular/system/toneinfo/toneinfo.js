/**
 * toneInfo管理初始化
 */
var Toneinfo = {
    id: "ToneinfoTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Toneinfo.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '歌曲id', field: 'songId', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '歌曲名称', field: 'songName', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '歌手名称', field: 'singerName', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter}
    ];
};

/**
 * 检查是否选中
 */
Toneinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    else{
    	 var arrays = new Array();// 声明一个数组
         $(selected).each(function () {// 通过获得别选中的来进行遍历
             arrays.push(this.id);// id为获得到的整条数据中的一列
         });
         var idcard = arrays.join(','); 
         Toneinfo.seItem = idcard;
        return true;
    }
};
//else if( selected.length == 1){
//	Toneinfo.seItem = selected[0];
//	return true;
//}
/**
 * 点击添加toneInfo
 */
Toneinfo.openAddToneinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加歌曲信息',
        area: ['800px', '420px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Feng.ctxPath + '/toneinfo/toneinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看toneInfo详情
 */
Toneinfo.openToneinfoDetail = function () {
    if (this.check()) {
    	//不能批量修改
    	if(Toneinfo.seItem.indexOf(",")!= -1){
    		Feng.info("修改只能选择一记录！");
    		return
    	}
        var index = layer.open({
            type: 2,
            title: 'toneInfo详情',
            area: ['800px', '420px'], // 宽高
            fix: false, // 不固定
            maxmin: true,
            content: Feng.ctxPath + '/toneinfo/toneinfo_update/' + Toneinfo.seItem
        });
        this.layerIndex = index;
    }
};

/**
 * 删除toneInfo
 */
Toneinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/toneinfo/delete", function (data) {
            Feng.success("删除成功!");
            Toneinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("toneinfoIds",this.seItem);
        ajax.start();
    }
};

/**
 * 查询toneInfo列表
 */
Toneinfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Toneinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Toneinfo.initColumn();
    var table = new BSTable(Toneinfo.id, "/toneinfo/list", defaultColunms);
    table.setPaginationType("client");
//  导出excle
    table.setIsShowExport(true);
    table.setFileName("歌曲信息导出");
    table.setWorksheetName("歌曲信息");
    Toneinfo.table = table.init();

});
/**
 * 批量导入歌曲信息
 */
 $('#import').fileinput({
 language: 'zh',
 uploadUrl: Feng.ctxPath + '/toneinfo/import',
 showCaption: true,
 showUpload: true,
 showRemove: true,
 showClose: true,
 layoutTemplates:{
 actionDelete: ''
 },
 browseClass: 'btn btn-primary'
 });
 /**
  * 导入后刷新
  */
 $("#import").on("fileuploaded", function(event, data, previewId, index) {
	 Toneinfo.table.refresh();
 });
 /**
  * 导入模板下载
  */
 Toneinfo.download = function () {
	 var $form = $('<form method="GET"></form>');
     $form.attr('action','/static/template/测试.xlsx');
     $form.appendTo($('body'));
     $form.submit();
 };
 
