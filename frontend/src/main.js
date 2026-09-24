import { createApp } from 'vue';
import './assets/main.css';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import router from './router';
import store from './store/index';
import * as ElIconList from '@element-plus/icons-vue';

const app = createApp(App);
app.use(ElementPlus);
app.use(router);
app.use(store);
app.mount('#app');

// 注册icon
for (const name in ElIconList) {
	app.component(name, ElIconList[name]);
}
