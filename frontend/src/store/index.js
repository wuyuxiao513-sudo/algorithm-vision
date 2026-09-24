import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

// 创建 Pinia 实例
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

// 导出 Pinia 实例以便将其与您的应用程序实例关联
export default pinia;
