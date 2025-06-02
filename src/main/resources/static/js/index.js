let switchCtn = document.querySelector("#switch-cnt");
let switchC1 = document.querySelector("#switch-c1");
let switchC2 = document.querySelector("#switch-c2");
let switchCircle = document.querySelectorAll(".switch__circle");
let switchBtn = document.querySelectorAll(".switch-btn");
let aContainer = document.querySelector("#a-container");
let bContainer = document.querySelector("#b-container");
let allButtons = document.querySelectorAll(".submit");

//验证码刷新函数
function refreshCaptcha() {
    document.querySelectorAll('img[id$="CaptchaImg"]').forEach(img => {
        img.src = '/app/Generate?' + Math.random();
    });
}

let getButtons = (e) => {
    // 如果按钮没有 lay-filter 属性，则阻止默认事件
    if (!e.target.hasAttribute("lay-filter")) {
        e.preventDefault();
    }
};

let changeForm = (e) => {
    switchCtn.classList.add("is-gx");
    setTimeout(function () {
        switchCtn.classList.remove("is-gx");
    }, 1500);

    switchCtn.classList.toggle("is-txr");
    switchCircle[0].classList.toggle("is-txr");
    switchCircle[1].classList.toggle("is-txr");

    switchC1.classList.toggle("is-hidden");
    switchC2.classList.toggle("is-hidden");
    aContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-z200");
    refreshCaptcha();
};

let mainF = (e) => {
    for (var i = 0; i < allButtons.length; i++)
        allButtons[i].addEventListener("click", getButtons);
    for (var i = 0; i < switchBtn.length; i++)
        switchBtn[i].addEventListener("click", changeForm);

    let isUsernameAvailable = false; // 标记用户名是否可用

    layui.use(['form', 'jquery', 'layer'], function() {
        const form = layui.form;
        const $ = layui.$;
        const layer = layui.layer;

        // 自定义校验规则
        form.verify({
            // 密码强度校验
            password: [/^[\S]{6,}$/, '密码至少需要6位'],

            // 确认密码一致性
            confirmPass: function(value) {
                const password = $('input[name="password"]').val();
                if (value !== password) return '两次密码输入不一致';
            },

            // 用户名实时校验
            usernameCheck: function(value) {
                if (!/^[a-zA-Z0-9]{4,20}$/.test(value)) {
                    $('#usernameTip').html('<span style="color:red">格式错误：4-20位字母/数字</span>');
                    return '用户名格式错误';
                }

                // 发送异步请求检查用户名是否存在
                $.ajax({
                    url: '/app/checkUsername',
                    method: 'POST',
                    data: { username: value },
                    async: false, // 同步请求确保校验顺序
                    success: function(res) {
                        if (res.available) {
                            $('#usernameTip').html('<span style="color:green">√ 用户名可用</span>');
                            isUsernameAvailable = true;
                        } else {
                            $('#usernameTip').html('<span style="color:red">× 用户名已存在</span>');
                            isUsernameAvailable = false;
                        }
                    }
                });

                if (!isUsernameAvailable) return '用户名不可用';
            }
        });

        // 注册处理
        form.on('submit(register)', function(data) {
            $.ajax({
                url: '/app/Through',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    username: data.field.username,
                    password: data.field.password,
                    captcha: data.field.captcha
                }),
                success: function(response) {
                    if(response.code === 200) {
                        layer.msg('注册成功', {icon: 1});
                        setTimeout(() => location.href = '/app/toLogin', 1500);
                    } else {
                        layer.msg(response.msg || '注册失败');
                        refreshCaptcha();
                    }
                },
                error: function(xhr) {
                    layer.msg(xhr.responseJSON?.msg || '服务器错误');
                    refreshCaptcha();
                }
            });
            return false;
        });

        // 登录
        form.on('submit(login)', function (data) {
            $.ajax({
                url: '/app/user/login',
                method: 'POST',
                data: {
                    username: data.field.username,
                    password: data.field.password,
                    captcha: data.field.captcha
                },
                success: function () {
                    layer.msg('登录成功 : ' + data.field.username)
                    window.location.href = '/app/home';
                },
                error: function (xhr) {
                    if (xhr.status === 403) {
                        layer.msg('验证码错误');
                    } else if (xhr.status === 401) {
                        layer.msg('用户名或密码错误');
                    } else {
                        layer.msg('登录失败，请检查网络');
                    }
                }
            });
            return false;
        });
    });
};

window.addEventListener("load", mainF);
