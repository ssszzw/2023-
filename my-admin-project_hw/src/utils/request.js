import axios, { CancelToken } from 'axios';
import {Message, MessageBox, Notification} from 'element-ui'
import store from '@/store'
import {getAccessToken, getRefreshToken, setToken} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {refreshToken} from "@/api/login";
import router from '@/router'

// 需要忽略的提示。忽略后，自动 Promise.reject('error')
const ignoreMsgs = [
  "无效的刷新令牌", // 刷新令牌被删除时，不用提示
  "刷新令牌已过期" // 使用刷新令牌，刷新获取新的访问令牌时，结果因为过期失败，此时需要忽略。否则，会导致继续 401，无法跳转到登出界面
]

// 是否显示重新登录
export let isRelogin = { show: false };
// Axios 无感知刷新令牌，参考 https://www.dashingdog.cn/article/11 与 https://segmentfault.com/a/1190000020210980 实现
// 请求队列
let requestList = []
// 是否正在刷新中
let isRefreshToken = false

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: '/api/', // 此处的 /admin-api/ 地址，原因是后端的基础路径为 /admin-api/
  // 超时
  timeout: 5000,
  // 禁用 Cookie 等信息
  withCredentials: false,
})
// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  // const isToken = (config.headers || {}).isToken === false
  if (getAccessToken() ) {
    config.headers['Authorization'] = 'Bearer ' + getAccessToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }

  // // get请求映射params参数
  // if (config.method === 'get' && config.params) {
  //   let url = config.url + '?';
  //   for (const propName of Object.keys(config.params)) {
  //     const value = config.params[propName];
  //     const part = encodeURIComponent(propName) + '='
  //     if (value !== null && typeof(value) !== "undefined") {
  //       if (typeof value === 'object') {
  //         for (const key of Object.keys(value)) {
  //           let params = propName + '[' + key + ']';
  //           const subPart = encodeURIComponent(params) + '='
  //           url += subPart + encodeURIComponent(value[key]) + "&";
  //         }
  //       } else {
  //         url += part + encodeURIComponent(value) + "&";
  //       }
  //     }
  //   }
  //   url = url.slice(0, -1);
  //   config.params = {};
  //   config.url = url;
  // }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(async res => {
  // 未设置状态码则默认成功状态
  const code = res.data.code || 200;
  // 获取错误信息
  const msg = res.data.msg || errorCode[code] || errorCode['default']

  console.log("code:",code,"----------msg",msg);

  if (ignoreMsgs.indexOf(msg) !== -1) { // 如果是忽略的错误码，直接返回 msg 异常
    return Promise.reject(msg)
  } else if (code === 401) {
    // 如果未认证，并且未进行刷新令牌，说明可能是访问令牌过期了
    if (!isRefreshToken) {
      isRefreshToken = true;
      // 1. 如果获取不到刷新令牌，则只能执行登出操作
      if (!getRefreshToken()) {
        return handleAuthorized();
      }
      // 2. 进行刷新访问令牌
      try {
        console.log("进入到了refreshToken");
        const refreshTokenRes = await refreshToken()
        // 2.1 刷新成功，则回放队列的请求 + 当前请求
        setToken(refreshTokenRes.data.data)
        requestList.forEach(cb => cb())

        return service(res.config)
      } catch (e) {// 为什么需要 catch 异常呢？刷新失败时，请求因为 Promise.reject 触发异常。
        // 2.2 刷新失败，只回放队列的请求
        console.log("进入到了refreshToken的error---");

        requestList.forEach(cb => cb())
        // 提示是否要登出。即不回放当前请求！不然会形成递归
        return handleAuthorized();
      } finally {
        requestList = []
        isRefreshToken = false
      }
    } else {
      // 添加到队列，等待刷新获取到新的令牌
      return new Promise(resolve => {
        requestList.push(() => {
          res.config.headers['Authorization'] = 'Bearer ' + getAccessToken() // 让每个请求携带自定义token 请根据实际情况自行修改
          resolve(service(res.config))
        })
      })
    }
  } else if (code === 500) {
    Message({
      message: msg,
      type: 'error'
    })
    return Promise.reject(msg)
  } else if (code === 501) {
    Message({
      type: 'error',
      duration: 0,
      message: msg
    })
    return Promise.reject(msg)
  } else if (code === 901) {
    Message({
      type: 'error',
      duration: 0,
      dangerouslyUseHTMLString: true,
      message: msg
    })
    return Promise.reject(msg)
  } else if (code !== 200) {
    if (msg === '无效的刷新令牌') { // hard coding：忽略这个提示，直接登出
      console.log(msg)
    } else {
      Notification.error({
        title: msg
      })
    }
    return Promise.reject(msg)
  } else {
    return res.data
  }
}, async error => {
    // console.log('err:-------------' + error)
    // console.log('error.response:-------------' + error.response);
    // console.log('error.response.data.code:-------------' + error.response.data.code);
    // console.log('error.response.data.msg:-------------' + error.response.data.msg);

    // console.log('error.response.status:-------------' + error.response.status);


  // 未设置状态码则默认成功状态
  const code = error.response.data.code || 200;
  // 获取错误信息
  const msg = error.response.data.msg || errorCode[code] || errorCode['default']

  console.log("code:",code,"----------msg",msg);

  if (ignoreMsgs.indexOf(msg) !== -1) { // 如果是忽略的错误码，直接返回 msg 异常
    return Promise.reject(msg)
  } else if (code === 401) {
    // 如果未认证，并且未进行刷新令牌，说明可能是访问令牌过期了
    if (!isRefreshToken) {
      isRefreshToken = true;
      // 1. 如果获取不到刷新令牌，则只能执行登出操作
      if (!getRefreshToken()) {
        return handleAuthorized();
      }
      // 2. 进行刷新访问令牌
      try {
        console.log("进入到了refreshToken");
        const refreshTokenRes = await refreshToken()
        console.log("await refreshToken()---:",refreshTokenRes)

        // 2.1 刷新成功，则回放队列的请求 + 当前请求
        setToken(refreshTokenRes.data)
        // console.log("setToken之后------");
        requestList.forEach(cb => cb());

        
        // console.log("error:---------",error);
        // console.log("error.response:---------",error.response);
        // console.log("requestList.forEach(cb => cb())之后----");
        // console.log("error.config:---------",error.config);

        return service(error.config)
      } catch (e) {// 为什么需要 catch 异常呢？刷新失败时，请求因为 Promise.reject 触发异常。
        // 2.2 刷新失败，只回放队列的请求
        console.log("进入到了refreshToken的error---");

        requestList.forEach(cb => cb())
        // 提示是否要登出。即不回放当前请求！不然会形成递归
        return handleAuthorized();
      } finally {
        requestList = []
        isRefreshToken = false
      }
    } else {
      // 添加到队列，等待刷新获取到新的令牌
      return new Promise(resolve => {
        requestList.push(() => {
          res.config.headers['Authorization'] = 'Bearer ' + getAccessToken() // 让每个请求携带自定义token 请根据实际情况自行修改
          resolve(service(res.config))
        })
      })
    }
  } else if (code === 500) {
    Message({
      message: msg,
      type: 'error'
    })
    return Promise.reject(msg)
  } else if (code === 501) {
    Message({
      type: 'error',
      duration: 0,
      message: msg
    })
    return Promise.reject(msg)
  } else if (code === 901) {
    Message({
      type: 'error',
      duration: 0,
      dangerouslyUseHTMLString: true,
      message: msg
    })
    return Promise.reject(msg)
  } else if (code !== 200) {
    if (msg === '无效的刷新令牌') { // hard coding：忽略这个提示，直接登出
      console.log(msg)
    } else {
      Notification.error({
        title: msg
      })
    }
    return Promise.reject(msg)
  } else {
    return res.data
  }










    let {message} = error;
    if (message === "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常999";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export function getBaseHeader() {
  return {
    'Authorization': "Bearer " + getAccessToken(),
  }
}

function handleAuthorized() {
  if (!isRelogin.show) {
    isRelogin.show = true;
    MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }
    ).then(() => {
      isRelogin.show = false;
      store.dispatch('LogOut').then(() => {
        router.push({ path: '/login' });
      })
    }).catch(() => {
      isRelogin.show = false;
    });
  }
  return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
}

export default service