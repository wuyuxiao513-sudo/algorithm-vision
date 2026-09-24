import axios from "axios";
import Cookie from "js-cookie";

// import { ElMessage } from "element";
// 1. 创建axios实例
const instance = axios.create({
  // 接口
  baseURL: "/api",
  // 超时时间
  timeout: 50000,
});
// 2.请求拦截
instance.interceptors.request.use(
  (config) => {
    let token = Cookie.get("token");
    let userId = Cookie.get("userId");
    if (token) {
      config.headers["token"] = token;
      config.headers["userId"] = userId;
    }
    return config;
  },
  (error) => {
    //  请求发生错误，抛出异常
    Promise.reject(error);
  }
);

// 导入 Pinia store
import { useUserStore } from "../../store/user"; // 这里是你 store 文件的路径

// 3.响应拦截
instance.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    if (response.data.msg == "token无效" || response.data.msg == "token过期") {
      // 通过 Pinia store 修改状态
      const userStore = useUserStore();

      userStore.setuserData({});
      userStore.setCenterDialogVisible(true);
      userStore.editLoginOrNot(false);
    }
    return response;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);
// 4.导出 axios 实例
export default instance;
