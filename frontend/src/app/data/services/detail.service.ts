import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DetailDto } from '../models/detail.dto';

@Injectable({ providedIn: 'root' })
export class DetailService {
  private apiUrl = 'details';

  constructor(private http: HttpClient) {}

  add(masterId: number, detail: DetailDto): Observable<DetailDto> {
    return this.http.post<DetailDto>(`${this.apiUrl}?masterId=${masterId}`, detail);
  }

  update(masterId: number, id: number, detail: DetailDto): Observable<DetailDto> {
    return this.http.put<DetailDto>(`${this.apiUrl}/${id}?masterId=${masterId}`, detail);
  }

  delete(masterId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}?masterId=${masterId}`);
  }
}
