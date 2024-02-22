import { login, userView, queryUserMenuList } from '@/api/login';
import { getToken, setToken, removeToken } from '@/utils/auth';
import defAva from '@/assets/images/avator_default.jpg';

export default defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      userInfo: {},
      menuRoutes: [], // 菜单
      isCollapse: false, // 菜单折叠开关
      roles: [],
      permissions: []
    }),
    actions: {
      // 登录
      login(data) {
        return new Promise((resolve, reject) => {
          login(data).then(res => {
            const hasPwd = res && res.userInfo && res.userInfo.hasPwd;
            setToken(res.token)
            this.token = res.token;
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 获取用户信息
      getUserInfo() {
        return new Promise((resolve, reject) => {
          userView().then(res => {
            this.userInfo = res.userInfo;
            this.userInfo.headImg = res.userInfo.headImg || defAva;
            this.permissions = res.auth.resIds.map(ele => +ele);
            // 获取菜单
            queryUserMenuList().then(res => {
              this.menuRoutes = setMenuProps(res.result.menuList);
            });
            resolve(res)
          }).catch(error => {
            removeToken();
            reject(error)
          })
        })
      },
      // 退出系统
      logOut() {
        return new Promise((resolve) => {
          this.token = '';
          this.userInfo = {};
          this.roles = [];
          this.permissions = [];
          removeToken();
          resolve();
        })
      }
    }
  });

  function setMenuProps(list = [], res = []) {
    list.forEach(item => {
      if (item.list && item.list.length) {
        item.children = setMenuProps(item.list);
      }
      res.push({
        ...item,
        title: item.menuName,
        path: item.menuUrl || ''
      });
    });
    return res;
  }
