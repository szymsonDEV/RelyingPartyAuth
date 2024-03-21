import axios from 'axios';

const TIMEOUT = 1000000;
const onRequestSuccess = config => {
  const token = sessionStorage.getItem('sdev_authenticationToken');

  if (token) {
    if (!config.headers) {
      config.headers = {};
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  // config.headers['Access-Control-Allow-Origin'] = process.env.VUE_APP_API_URL;
  config.timeout = TIMEOUT;
  config.url = `${process.env.VUE_APP_API_URL}${config.url}`;
  return config;
};
const setupAxiosInterceptors = onUnauthenticated => {
  const onResponseError = err => {
    const status = err.status || err.response.status;
    if (status === 403 || status === 401) {
      onUnauthenticated();
    }
    return Promise.reject(err);
  };
  if (axios.interceptors) {
    axios.interceptors.request.use(onRequestSuccess);
    axios.interceptors.response.use(res => res, onResponseError);
  }
};

export { onRequestSuccess, setupAxiosInterceptors };
