import { defineStore } from "pinia";

function getExpiredStorage(storage, expiresTime) {
  //自定义过期时间
  return {
    getItem(key) {
      const itemStr = storage.getItem(key);
      if (!itemStr) {
        return null;
      }
      // 读取的时候是字符串，需要转换为对象
      const item = JSON.parse(itemStr);
      const now = new Date();
      if (now.getTime() > item.expiry) {
        storage.removeItem(key);
        return null;
      }
      // pinia 的持久化插件要求返回JSON字符串
      return JSON.stringify(item.value);
    },
    setItem(key, value) {
      const now = new Date();
      const item = {
        value: JSON.parse(value), // value是JSON字符串，需要转换为对象
        expiry: now.getTime() + Number(expiresTime) * 1000,
      };
      storage.setItem(key, JSON.stringify(item)); // 存储又要转为JSON字符串
    },
    removeItem(key) {
      storage.removeItem(key);
    },
    clear() {
      Object.keys(storage).forEach((key) => {
        storage.removeItem(key);
      });
    },
    key(index) {
      const keys = Object.keys(storage);
      return keys[index] || null;
    },
    length: Object.keys(storage).length,
  };
}

const expiresTime = 60 * 60 * 24 * 7; // 自定义过期时间 7天
const expiredStorage = getExpiredStorage(localStorage, expiresTime);

export const useUserStore = defineStore({
  id: "user", // id 是为了更好地区分模块
  state: () => ({
    // 登录用户数据
    userData: {},
    // 切换登录和未登录显示
    loginOrNot: false,
    // 登录列表弹窗
    centerDialogVisible: false,
    //导航切换效果
    selectedIndex: 0,

    searchData: "",
  }),
  actions: {
    // 定义操作或者异步请求
    setuserData(data) {
      this.userData = data;
    },
    editLoginOrNot(flag) {
      this.loginOrNot = flag;
    },
    setCenterDialogVisible(flag) {
      this.centerDialogVisible = flag;
    },
    setSelectedIndex(index) {
      this.selectedIndex = index;
    },
    setsearchData(data) {
      this.searchData = data;
    },
  },
  persist: {
    storage: expiredStorage,
    debug: true, // 调试模式
  },
});
