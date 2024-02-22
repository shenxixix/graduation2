import { dictData } from '@/utils/dict';

export default defineStore(
  'dict',
  {
    state: () => ({
      dict: new Array()
    }),
    actions: {
      // 获取字典
      getDict(_key) {
        if (!_key) {
          return [];
        }
        // 默认从state获取
        const act = this.dict.find(ele => ele.key === _key);
        if (act) {
          return act.value;
        }
        // 如果没有值，从dictData获取，并注册到state，todo:后期从接口获取
        const value = dictData[_key];
        if (!value) {
          return [];
        }
        this.setDict(_key, value);
        return value;
      },
      // 设置字典
      setDict(key, value) {
        if (key) {
          this.dict.push({ key, value });
        }
      },
      // 删除字典
      removeDict(_key) {
        var bln = false;
        try {
          for (let i = 0; i < this.dict.length; i++) {
            if (this.dict[i].key == _key) {
              this.dict.splice(i, 1);
              return true;
            }
          }
        } catch (e) {
          bln = false;
        }
        return bln;
      },
      // 清空字典
      cleanDict() {
        this.dict = new Array();
      },
      // 初始字典
      initDict() {
      }
    }
  })
