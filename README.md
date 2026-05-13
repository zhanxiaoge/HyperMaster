# HyperMaster 项目总结

> 我的HyperOS，按我的方式增强。My HyperOS. Enhanced my way.

Xposed 模块 + Miuix UI 应用，用于增强小米 HyperOS 系统体验。

## 基本信息

- **包名**: `com.zhanxiaoge.hypermaster`
- **类型**: Xposed 模块 + Miuix UI 应用
- **最低 SDK**: 36（Android 16）
- **编译 SDK**: 37
- **架构**: arm64-v8a（x86_64 用于模拟器调试）

## 仓库地址

- **本项目代码**: https://github.com/zhanxiaoge/HyperMaster.git
- **libxposed API**: https://github.com/libxposed/api （Maven: `io.github.libxposed:api`）
- **Miuix UI**: https://github.com/compose-miuix-ui/miuix （Maven: `top.yukonga.miuix.kmp:miuix-ui`）

## 技术栈

| 技术          | 版本    | 说明             |
| ------------- | ------- | ---------------- |
| Kotlin        | 2.3.21  | 语言             |
| AGP           | 9.2.1   | Android 构建工具 |
| Gradle        | 9.5.0   | 构建系统         |
| Miuix UI      | 0.9.0   | UI 框架          |
| libxposed API | 101.0.1 | Xposed 框架 API  |

## 目录结构 (`app/src/main/kotlin/com/zhanxiaoge/hypermaster/`)

```
├── HyperApp.kt                     # Application 入口，持有 DataHub 单例
├── common/                         # ── ui 可复用组件 ──
├── data/                           # ── 数据层 ──
│   ├── BaseManager.kt              # DataStore 基类，响应式读写 + IOException 降级
│   ├── DataHub.kt                  # 全局数据入口（单例），持有所有 Manager
│   ├── DataKeys.kt                 # Preferences Key 集中管理
│   ├── DataWhitelist.kt            # DataStore 清理字段白名单
│   └── ...
├── main/                           # ── 应用入口 ──
│   ├── MainActivity.kt             # 应用主入口 Activity
│   └── MainViewModel.kt            # 应用主入口 ViewModel
├── navigation/                     # ── 导航层 ──
│   ├── NavAction.kt                # Navigation3 导航操作封装
│   ├── NavHub.kt                   # Navigation3 导航中心
│   └── NavScreen.kt                # 路由清单管理
├── ui/                             # ── 页面层 ──
├── utils/                          # ── 工具层 ──
│   ├── ComposeExt.kt               # StateFlow 局部字段观察，减少无效重组
│   ├── CoroutineScopeExt.kt        # 协程节流启动器，防止短时间内重复执行任务
│   ├── Throttle.kt                 # 操作节流器，在指定毫秒内重复调用会被拦截
│   └── ...
└── xposed/                         # ── Hook 层 ──
    ├── HookEntry.kt                # Xposed 模块入口，继承 XposedModule
    └── ...
```

### 数据层架构

```
HyperApp.dataHub (单例)
 └── DataHub
      └── system: SystemManager (继承 BaseManager)
           └── DataStore<Preferences>  ← 响应式 Flow 读写
```

- **BaseManager** — 封装 `getValue()` / `setValue()` 泛型操作，IOException 时降级为空 Preferences
- **DataKeys** — 集中管理所有 Preferences Key，避免硬编码字符串
- **DataWhitelist** — 定义 `DataHub.reset()` 时需要保留的 key 规则（支持通配符 `*`）

### Xposed 配置

- **入口类**: `com.zhanxiaoge.hypermaster.xposed.HookEntry`
- **元数据**: `resources/META-INF/xposed/` 下的 `java_init.list`, `scope.list`, `module.prop`

## 开发规范

### UI 开发

**所有 UI 组件必须使用 Miuix，不混用其他 UI 框架。**

- 组件文档: https://compose-miuix-ui.github.io/miuix/dokka/index.html
- 基础组件: `top.yukonga.miuix.kmp.basic.*`（Text, Button, Card 等，具体请看组件文档）
- 不使用 `androidx.compose.material3` 或 `androidx.compose.material` 的等组件，仅用 Miuix 组件

### Git 分支工作流

**所有代码修改必须在独立分支上进行，禁止直接在 main 上提交。**

```bash
# 1. 先拉取最新 main
git checkout main
git pull origin main

# 2. 从 main 创建功能分支
git checkout -b feat/你的功能名

# 3. 在分支上开发、提交
git add .
git commit -m "feat: 描述你的改动"

# 4. 推送分支到远程（需项目负责人同意）
git push origin feat/你的功能名
```

分支命名建议：

- `feat/xxx` — 新功能
- `fix/xxx` — 修复
- `refactor/xxx` — 重构
- `docs/xxx` — 文档

### 提交规则

- **所有代码推送必须经项目负责人同意后才能提交到仓库**
- Commit Message 遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范

## AI 工作规则

### 收到项目相关问题时

1. **第一步必须拉取/更新代码**，基于实际仓库代码分析，不凭空推测
2. **建议任何 API、配置、依赖前，先验证当前版本是否支持**，不推荐已废弃或未发布的功能
3. **ProGuard 规则基于实际代码结构分析**，不笼统 keep 整个包
4. **构建相关建议前，先检查 `build.gradle.kts`、`libs.versions.toml` 等配置文件的实际内容**

### 代码修改规则

1. **所有代码修改必须在独立分支上进行**，禁止直接在 main 上提交
2. **禁止未经项目负责人同意就推送代码到远程仓库**
3. **禁止未经同意就创建 Pull Request**
4. **修改完成后先本地提交，等待负责人确认后再推送**
