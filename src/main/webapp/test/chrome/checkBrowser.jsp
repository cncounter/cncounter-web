<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>checkBrowser</title>
    <script type="text/javascript">
        var startTime = new Date();
    </script>

</head>
<body>
<h1>checkBrowser</h1>

<h2> - checkBrowser 示例</h2>

<p>
    到Gist查看代码: <a target="_blank" href="https://gist.github.com/renfufei/1d75ee85abb6ad7a2d04f1d4f7600990">https://gist.github.com/renfufei/1d75ee85abb6ad7a2d04f1d4f7600990</a>
</p>

<p>
    <a href="./loadTimes.jsp"  target="_blank">审查页面加载时间</a>
</p>
<table border="1">
    <thead>
    <tr>
        <th>说明</th>
        <th>字段</th>
        <th>值</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td>isIE</td>
        <td>isIE</td>
        <td id="isIE"></td>
    </tr>
    <tr>
        <td>ieVersion</td>
        <td>ieVersion</td>
        <td id="ieVersion"></td>
    </tr>
    <tr>
        <td>isChrome</td>
        <td>isChrome</td>
        <td id="isChrome"></td>
    </tr>
    <tr>
        <td>is360se</td>
        <td>is360se</td>
        <td id="is360se"></td>
    </tr>
    <tr>
        <td>is360ee</td>
        <td>is360ee</td>
        <td id="is360ee"></td>
    </tr>
    <tr>
        <td>isLiebao</td>
        <td>isLiebao</td>
        <td id="isLiebao"></td>
    </tr>
    <tr>
        <td>isSogou</td>
        <td>isSogou</td>
        <td id="isSogou"></td>
    </tr>
    <tr>
        <td>isQQ</td>
        <td>isQQ</td>
        <td id="isQQ"></td>
    </tr>
    </tbody>

</table>

<div>
    <h3>返回结果</h3>
    <textarea id="resultJSON" rows="12" cols="48"></textarea>
</div>

<div>
    <h3>使用示例</h3>
    <textarea id="sample" rows="12" cols="48"></textarea>
</div>

<script>

    (function (module, pName) {
        'use strict';
        var win = window;
        var nav = win.navigator;
        var doc = win.document;
        var ieAX = win.ActiveXObject;
        var ieMode = doc.documentMode;
        var REG_APPLE = /^Apple/;
        var ieVer = _getIeVersion() || ieMode || 0;
        var isIe = ieAX || ieMode;
        var chromiumType = _getChromiumType();
        module[pName] = {
            /**
             * 判断是否为 IE 浏览器
             */
            isIE: (function () {
                return !!ieVer;
            })(),
            /**
             * IE 版本
             * // 6/7/8/9/10/11/12...
             */
            ieVersion: (function () {
                return ieVer;
            })(),
            /**
             * 是否为谷歌 chrome 浏览器
             */
            isChrome: (function () {
                return chromiumType === 'chrome';
            })(),
            /**
             * 是否为360安全浏览器
             */
            is360se: (function () {
                return chromiumType === '360se';
            })(),
            /**
             * 是否为360极速浏览器
             */
            is360ee: (function () {
                return chromiumType === '360ee';
            })(),
            /**
             * 是否为猎豹安全浏览器
             */
            isLiebao: (function () {
                return chromiumType === 'liebao';
            })(),
            /**
             * 是否搜狗高速浏览器
             */
            isSogou: (function () {
                return chromiumType === 'sogou';
            })(),
            /**
             * 是否为 QQ 浏览器
             */
            isQQ: (function () {
                return chromiumType === 'qq';
            })()
        };


        /**
         * 检测 external 是否包含该字段
         * @param reg 正则
         * @param type 检测类型，0为键，1为值
         * @returns {boolean}
         * @private
         */
        function _testExternal(reg, type) {
            var external = win.external || {};

            for (var i in external) {
                if (reg.test(type ? external[i] : i)) {
                    return true;
                }
            }

            return false;
        }


        /**
         * 获取 Chromium 内核浏览器类型
         * @link http://www.adtchrome.com/js/help.js
         * @link https://ext.chrome.360.cn/webstore
         * @link https://ext.se.360.cn
         * @return {String}
         *         360ee 360极速浏览器
         *         360se 360安全浏览器
         *         sougou 搜狗浏览器
         *         liebao 猎豹浏览器
         *         chrome 谷歌浏览器
         *         ''    无法判断
         * @version 1.0
         * 2014年3月12日20:39:55
         */

        function _getChromiumType() {
            if (isIe || typeof win.scrollMaxX !== 'undefined' || REG_APPLE.test(nav.vendor || '')) {
                return '';
            }

            var _track = 'track' in document.createElement('track');
            var webstoreKeysLength = win.chrome && win.chrome.webstore ? Object.keys(win.chrome.webstore).length : 0;

            // 搜狗浏览器
            if (_testExternal(/^sogou/i, 0)) {
                return 'sogou';
            }

            // 猎豹浏览器
            if (_testExternal(/^liebao/i, 0)) {
                return 'liebao';
            }

            // chrome
            if (win.clientInformation && win.clientInformation.languages && win.clientInformation.languages.length > 2) {
                return 'chrome';
            }

            if (_track) {
                // 360极速浏览器
                // 360安全浏览器
                return webstoreKeysLength > 1 ? '360ee' : '360se';
            }

            return '';
        }


        // 获得ie浏览器版本
        function _getIeVersion() {
            var v = 3,
                    p = document.createElement('p'),
                    all = p.getElementsByTagName('i');

            while (
                    p.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
                            all[0]);

            return v > 4 ? v : 0;
        }

    })(window, "browserCheck");
</script>
<script>

    // 该函数参见: http://www.cncounter.com/static/js/cncounter-util.js?v=1
    // 遍历对象属性
    function foreachProperty(obj, fn) {
        if (!obj || !fn) {
            return;
        }
        for (var name in obj) {
            if (!name) {
                continue;
            }
            var isSelfProperty = Object.prototype.hasOwnProperty.call(obj, name);
            if (isSelfProperty) { // 自有属性,回调
                var value = obj[name];
                var result = fn(name, value, obj);
                if (false === result) {
                    break;
                }
            }
        }
    }
    ;
    //
    function getBrowserInfo() {
        var browserInfo = {};
        // 调用 Chrome
        if (window["browserCheck"]) {
            browserInfo = window.browserCheck;
        } else {
            // 填充其他值?
        }
        //
        return browserInfo;
    }
    ;
    //
    function toRender(browserInfo) {
        //
        foreachProperty(browserInfo, function (n, v) {
            //
            (document.getElementById(n) || {}).innerText = v;
        });
        //
        var str = JSON.stringify(browserInfo);
        //
        str = str.replace("{", "{\n    ").replace("}", "\n}").replace(/,/g, "\n    ,");
        //
        document.querySelector("#resultJSON").innerText = str;

    }
    ;
    //
    window.setTimeout(function () {
        var browserInfo = getBrowserInfo();
        //
        toRender(browserInfo);
    }, 10);


</script>
</body>
</html>
