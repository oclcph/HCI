# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about the recommended Project Setup and IDE Support in the [Vue Docs TypeScript Guide](https://vuejs.org/guide/typescript/overview.html#project-setup).

## 项目框架

frontend/
├── node_modules/      # npm 安装的依赖
├── public/            # 公共资源
├── src/               
│   ├── assets/        # 静态资源，如图片、字体等
│   ├── components/    # 可重用的 Vue 组件
│   │   └── Header.vue # 头部组件，包含登录按钮
│   ├── views/         # 页面组件
│   │   └── Home.vue   # 首页，包含填空内容
│   ├── router/        # 路由配置
│   │   └── index.ts   # Vue Router 配置
│   ├── store/         # Pinia 状态管理
│   │   └── userStore.ts # 用户状态管理
│   ├── api/           # API 请求封装
│   ├── styles/        # 全局样式
│   ├── i18n/          # 国际化配置
│   └── main.ts        # 入口文件
├── tailwind.config.js # Tailwind 配置文件
├── postcss.config.js  # Tailwind 配置文件
├── package.json       # 项目的配置文件
├── tsconfig.json      # TypeScript 配置文件
├── README.md          # 此文件
└── vite.config.ts     # Vite 配置文件
