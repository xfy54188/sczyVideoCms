/**
 * 日志统计管理初始化
 */
var ActivityLog = {
    id: "ActivityLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ActivityLog.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '手机号', field: 'msdn', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '子渠道标识', field: 'subchannel', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '操作类型', field: 'action', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '页面名称', field: 'pageName', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: 'opensdk 返回码', field: 'returncode', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: 'opensdk 返回描述', field: 'returndesc', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '版权ID', field: 'cpid', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '页面url', field: 'pageUrl', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '用户ip地址', field: 'ip', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '操作时间', field: 'updatetime', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter}
    ];
};

/**
 * 检查是否选中
 */
ActivityLog.check = function () {
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
        ActivityLog.seItem = idcard;
        return true;
    }
};

/**
 * 点击添加日志统计
 */
ActivityLog.openAddActivityLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加日志统计',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activityLog/activityLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看日志统计详情
 */
ActivityLog.openActivityLogDetail = function () {
    if (this.check()) {
        //不能批量修改
        if(ActivityLog.seItem.indexOf(",")!= -1){
            Feng.info("修改只能选择一记录！");
            return
        }
        var index = layer.open({
            type: 2,
            title: '日志统计详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/activityLog/activityLog_update/' + ActivityLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除日志统计
 */
ActivityLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/activityLog/delete", function (data) {
            Feng.success("删除成功!");
            ActivityLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("activityLogIds",this.seItem);
        ajax.start();
    }
};

/**
 * 查询日志统计列表
 */
ActivityLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['logTime'] = $("#logTime").val();
    ActivityLog.table.refresh({query: queryData});
};

$(function () {
    var account= $("#account").val();
    var defaultColunms = ActivityLog.initColumn();
    var table = new BSTable(ActivityLog.id, "/activityLog/list/"+account+"", defaultColunms);
    table.setPaginationType("client");
    //  导出excle
    table.setIsShowExport(true);
    table.setFileName("操作日志导出");
    table.setWorksheetName("日志信息");
    ActivityLog.table = table.init();
});
