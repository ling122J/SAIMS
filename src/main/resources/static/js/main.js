layui.use(['element', 'jquery', 'table', 'layer'], function () {
    const element = layui.element;
    const $ = layui.$;
    const table = layui.table;
    const layer = layui.layer;

    // 注销功能
    $('#logout').click(function () {
        location.href = '/app/toLogout';
    });

    // ====================== 角色列表逻辑 ======================
    $('.layui-nav-item a[data-url="/role/list"]').on('click', function () {
        const url = $(this).data('url');
        const type = $(this).data('type');

        $.ajax({
            url: '/app' + url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({page: 1, size: 10}),
            success: function (res) {
                if (res.code === 0) {
                    // 插入表格容器
                    $('#contentArea').html(`<div class="layui-card"><div class="layui-card-body">
                        <table id="dynamicTable" lay-filter="dynamicTable"></table></div></div>`);
                    // 构建列配置
                    const cols = [
                        {field: 'rid', title: '角色ID', width: 100},
                        {field: 'roleCode', title: '角色编码', width: 200},
                        {field: 'roleName', title: '角色名称', width: 200},
                        {title: '操作', toolbar: '#roleBar', width: 300}
                    ];
                    // 渲染表格
                    table.render({
                        elem: '#dynamicTable',
                        data: res.data,
                        page: true,
                        cols: [cols],
                        limits: [5,10,20,50],
                        parseData: function (r) {
                            return {
                                "code": 0,
                                "msg": r.message,
                                "count": r.count,
                                "data": r.data
                            };
                        }
                    });
                    // 工具条事件监听
                    bindRoleTableEvents(type);
                } else {
                    layer.msg('数据加载失败：' + res.message);
                }
            },
            error: function () {
                layer.msg('请求失败，请检查网络或登录状态');
            }
        });
    });

    // 角色列表操作栏事件处理
    function bindRoleTableEvents(type) {
        table.on('tool(dynamicTable)', function (obj) {
            const data = obj.data;
            if (obj.event === 'edit') {
                // 编辑角色逻辑
                layer.open({
                    type: 1,
                    title: '编辑角色',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <input type="hidden" id="rid" value="${data.rid}">
                        <div class="layui-form-item">
                            <label class="layui-form-label">角色编码</label>
                            <div class="layui-input-block">
                                <input type="text" id="roleCode" value="${data.roleCode}" class="layui-input" required>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">角色名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="roleName" value="${data.roleName}" class="layui-input" required>
                            </div>
                        </div>
                    </div>
                `,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const payload = {
                            rid: data.rid,
                            roleCode: $('#roleCode').val(),
                            roleName: $('#roleName').val()
                        };
                        $.ajax({
                            url: '/app/role/update',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(payload),
                            success: function (res) {
                                if (res.code === 0) {
                                    layer.msg('更新成功');
                                    table.reload('dynamicTable'); // 刷新表格
                                    layer.close(index);
                                }
                            },
                            error: function (xhr) {
                                const res = xhr.responseJSON;
                                layer.msg(res?.message || '更新失败');
                            }
                        });
                    }
                });
            } else if (obj.event === 'del') {
                // 删除角色逻辑
                layer.confirm('确认删除该角色？', function (index) {
                    $.ajax({
                        url: '/app/role/delete',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({ rid: data.rid }),
                        success: function () {
                            table.reload('dynamicTable'); // 刷新表格
                            layer.close(index);
                        },
                        error: function (xhr) {
                            const res = xhr.responseJSON;
                            layer.msg(res?.message || '删除失败');
                        }
                    });
                });
            } else if (obj.event === 'bindUser') {  // 新增绑定用户逻辑
                layer.open({
                    type: 1,
                    title: '绑定用户到角色',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户ID列表</label>
                            <div class="layui-input-block">
                                <input type="text" id="uidList" class="layui-input" placeholder="多个ID用逗号分隔" required>
                            </div>
                        </div>
                    </div>`,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const uidList = $('#uidList').val().split(',').map(id => parseInt(id.trim()));
                        if (uidList.some(isNaN)) {
                            layer.msg('请输入有效用户ID');
                            return;
                        }
                        $.ajax({
                            url: '/app/role/bindUsers',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({rid: data.rid, uidList: uidList}),
                            success: function (res) {
                                layer.msg(res.message || '绑定成功');
                                layer.close(index);
                            },
                            error: function (xhr) {
                                const res = xhr.responseJSON;
                                layer.msg(res?.message || '绑定失败');
                            }
                        });
                    }
                });
            } else if (obj.event === 'add') { // 新增添加角色逻辑
                layer.open({
                    type: 1,
                    title: '添加角色',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <div class="layui-form-item">
                            <label class="layui-form-label">角色编码</label>
                            <div class="layui-input-block">
                                <input type="text" id="roleCode" class="layui-input" placeholder="例如：admin" required>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">角色名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="roleName" class="layui-input" placeholder="例如：管理员" required>
                            </div>
                        </div>
                    </div>
                `,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const payload = {
                            roleCode: $('#roleCode').val(),
                            roleName: $('#roleName').val()
                        };
                        // 校验输入
                        if (!payload.roleCode || !payload.roleName) {
                            layer.msg('请填写完整信息');
                            return;
                        }
                        // 发送添加请求
                        $.ajax({
                            url: '/app/role/add',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(payload),
                            success: function (res) {
                                if (res.code === 0) {
                                    layer.msg('添加成功');
                                    table.reload('dynamicTable', { page: { curr: 1 } }); // 刷新并跳转第一页
                                    layer.close(index);
                                } else {
                                    layer.msg(res.message || '添加失败');
                                }
                            },
                            error: function (xhr) {
                                const res = xhr.responseJSON;
                                layer.msg(res?.message || '请求失败');
                            }
                        });
                    }
                });
            }
        });
    }

    // ====================== 用户列表逻辑 ======================
    $('.layui-nav-item a[data-url="/user/list"]').on('click', function () {
        const url = $(this).data('url');
        const type = $(this).data('type');

        $.ajax({
            url: '/app' + url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({page: 1, size: 10}),
            success: function (res) {
                if (res.code === 0) {
                    $('#contentArea').html(`<div class="layui-card"><div class="layui-card-body">
                        <table id="dynamicTable" lay-filter="dynamicTable"></table></div></div>`);
                    const cols = [
                        {field: 'uid', title: '用户ID', width: 100},
                        {field: 'username', title: '用户名', width: 200},
                        {
                            field: 'enabled',
                            title: '状态',
                            templet: function (d) {
                                return d.enabled ? '<span style="color:green">启用</span>' : '<span style="color:red">禁用</span>';
                            },
                            width: 100
                        },
                        {title: '操作', toolbar: '#userBar', width: 300}
                    ];
                    table.render({
                        elem: '#dynamicTable',
                        data: res.data,
                        page: true,
                        limits: [5,10,20,50],
                        cols: [cols],
                        parseData: function (r) {
                            return {
                                "code": 0,
                                "msg": r.message,
                                "count": r.count,
                                "data": r.data
                            };
                        }
                    });
                    // 绑定用户操作栏事件
                    bindUserTableEvents();
                }
            },
            error: function (xhr) {
                const res = xhr.responseJSON;
                layer.msg(res?.message || '请求失败');
            }
        });
    });

    // 用户列表操作栏事件处理
    function bindUserTableEvents() {
        table.on('tool(dynamicTable)', function (obj) {
            const data = obj.data;
            if (obj.event === 'disable') {
                layer.confirm('确定' + (data.enabled ? '禁用' : '启用') + '该用户？', function (index) {
                    $.ajax({
                        url: '/app/user/updateStatus',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({uid: data.uid, enabled: !data.enabled}),
                        success: function (res) {
                            if (res.code === 0) {
                                table.reload('dynamicTable');
                                layer.close(index);
                            }
                        },
                        error: function (xhr) {
                            const res = xhr.responseJSON;
                            layer.msg(res?.message || '请求失败');
                        }
                    });
                });
            } else if (obj.event === 'grant') {
                layer.msg('赋权功能待实现');
            } else if (obj.event === 'bindRole') {
                layer.open({
                    type: 1,
                    title: '绑定角色到用户',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <div class="layui-form-item">
                            <label class="layui-form-label">角色ID列表</label>
                            <div class="layui-input-block">
                                <input type="text" id="ridList" class="layui-input" placeholder="多个ID用逗号分隔" required>
                            </div>
                        </div>
                    </div>`,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const ridList = $('#ridList').val().split(',').map(id => parseInt(id.trim()));
                        if (ridList.some(isNaN)) {
                            layer.msg('请输入有效角色ID');
                            return;
                        }
                        $.ajax({
                            url: '/app/role/bindRoles',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({uid: data.uid, ridList: ridList}),
                            success: function (res) {
                                layer.msg(res.message || '绑定成功');
                                layer.close(index);
                            },
                            error: function (xhr) {
                                const res = xhr.responseJSON;
                                layer.msg(res?.message || '绑定失败');
                            }
                        });
                    }
                });
            }
        });
    }

    // ====================== 权限列表逻辑 ======================
    // 监听权限列表点击事件
    $('.layui-nav-item a[data-url="/permission/list"]').on('click', function () {
        const url = $(this).data('url');
        // 发送AJAX请求
        $.ajax({
            url: '/app' + url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ page: 1, size: 10 }),
            success: function (res) {
                if (res.code === 0) {
                    // 渲染表格
                    $('#contentArea').html(`
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <table id="dynamicTable" lay-filter="dynamicTable"></table>
                        </div>
                    </div>
                `);
                    // 列配置
                    const cols = [
                        { field: 'pid', title: '权限ID', width: 100 },
                        { field: 'permissionName', title: '权限名称', width: 200 },
                        { field: 'permissionCode', title: '权限编码', width: 200 },
                        { field: 'resourceUrl', title: '资源路径', width: 250 },
                        { field: 'resourceType', title: '资源类型', width: 150 },
                        { title: '操作', toolbar: '#permBar', width: 200 }
                    ];
                    // 渲染表格
                    table.render({
                        elem: '#dynamicTable',
                        data: res.data,
                        page: true,
                        limits: [5,10,20,50],
                        cols: [cols],
                        parseData: function (r) {
                            return {
                                "code": 0,
                                "msg": r.message,
                                "count": r.count,
                                "data": r.data
                            };
                        }
                    });
                    // 绑定权限操作事件
                    bindPermissionTableEvents();
                }
            }
        });
    });
    /* 权限列表工具条事件处理 */
    function bindPermissionTableEvents() {
        table.on('tool(dynamicTable)', function (obj) {
            const data = obj.data;
            if (obj.event === 'edit') {
                // 编辑权限弹窗
                layer.open({
                    type: 1,
                    title: '编辑权限',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <input type="hidden" id="pid" value="${data.pid}">
                        <div class="layui-form-item">
                            <label class="layui-form-label">权限名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="permissionName" value="${data.permissionName}" class="layui-input" required>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">权限编码</label>
                            <div class="layui-input-block">
                                <input type="text" id="permissionCode" value="${data.permissionCode}" class="layui-input" required>
                            </div>
                        </div>
                    </div>
                `,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const payload = {
                            pid: data.pid,
                            permissionName: $('#permissionName').val(),
                            permissionCode: $('#permissionCode').val()
                        };
                        // 发送更新请求
                        $.ajax({
                            url: '/app/permission/update',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(payload),
                            success: function (res) {
                                if (res.code === 0) {
                                    layer.msg('更新成功');
                                    table.reload('dynamicTable'); // 刷新表格
                                    layer.close(index);
                                }
                            },
                            error: function (res) {
                                layer.msg(res.message || '更新失败'); // 显示后端返回的具体错误
                            }
                        });
                    }
                });
            } else if (obj.event === 'del') {
                // 删除权限
                layer.confirm('确认删除该权限？', function (index) {
                    $.ajax({
                        url: '/app/permission/delete',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({ pid: data.pid }),
                        success: function () {
                            table.reload('dynamicTable'); // 刷新表格
                            layer.close(index);
                        },
                        error: function (res) {
                            layer.msg(res.message || '删除失败'); // 显示后端返回的具体错误
                        }
                    });
                });
            } else if (obj.event === 'add') {
                // 添加权限弹窗
                layer.open({
                    type: 1,
                    title: '添加权限',
                    content: `
                    <div class="layui-form" style="padding:20px;">
                        <div class="layui-form-item">
                            <label class="layui-form-label">权限名称</label>
                            <div class="layui-input-block">
                                <input type="text" id="permissionName" class="layui-input" required>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">权限编码</label>
                            <div class="layui-input-block">
                                <input type="text" id="permissionCode" class="layui-input" required>
                            </div>
                        </div>
                    </div>
                `,
                    btn: ['提交', '取消'],
                    area: '500px',
                    yes: function (index) {
                        const payload = {
                            permissionName: $('#permissionName').val(),
                            permissionCode: $('#permissionCode').val()
                        };

                        // 发送添加请求
                        $.ajax({
                            url: '/app/permission/add',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(payload),
                            success: function (res) {
                                if (res.code === 0) {
                                    layer.msg('添加成功');
                                    table.reload('dynamicTable', { page: { curr: 1 } }); // 刷新并跳转第一页
                                    layer.close(index);
                                }
                            },
                            error: function (res) {
                                layer.msg(res.message || '添加失败'); // 显示后端返回的具体错误
                            }
                        });
                    }
                });
            }
        });
    }
});