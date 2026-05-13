package com.zhanxiaoge.hypermaster.data

/**
 * DataStore 字段白名单
 * 用于控制 DataHub.reset() 时的 Key 保留规则
 * - 精确匹配: `sys_is_first_run_done`
 * - 通配符: `user_*`（前缀）、`*_status`（后缀）
 * - 注意: 规则中不要写 `.*`，因为 `.` 会被转义为字面量点号
 */
object DataWhitelist {

    // 请在此填写字段白名单
    private val RAW_RULES = setOf(
        "",
    )

    // 预编译好的正则列表
    val RULES: List<Regex> by lazy {
        RAW_RULES.filter { it.isNotBlank() }.map { pattern ->
            // 1. 转义点号：使 . 变为普通的点
            val escapedDot = pattern.replace(".", "\\.")

            // 2. 转换通配符：使 * 变为正则的 .*
            val escapedStar = escapedDot.replace("*", ".*")

            // 3. 增加首尾锚点：确保全字匹配
            val finalPattern = "^$escapedStar$"

            // 4. 编译并返回
            finalPattern.toRegex()
        }
    }

}
