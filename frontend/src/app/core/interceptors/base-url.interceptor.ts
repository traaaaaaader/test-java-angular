import { HttpInterceptorFn } from '@angular/common/http';

const API_BASE_URL = 'http://localhost:8080/api';

export const baseUrlInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.startsWith('http://') || req.url.startsWith('https://')) {
    return next(req);
  }

  const apiReq = req.clone({ url: `${API_BASE_URL}/${req.url}` });
  return next(apiReq);
};
