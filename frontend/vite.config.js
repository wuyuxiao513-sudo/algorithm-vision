import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { config } from "./config"; // 引入配置文件

export default defineConfig({
  plugins: [vue()],
  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true,
      },
    },
  },
  server: {
    // 更换端口号
    port: 8091,
    host: "0.0.0.0", // 允许外部设备访问
    proxy: {
      // 代理配置
      "^/api": {
        target: config.target, // 使用配置文件中的值
        changeOrigin: true, // 开启跨域
        rewrite: (path) => path.replace(/^\/api/, ""), // 将请求地址中的 /api 替换为空
      },
    },
  },
});
