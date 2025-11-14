import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MasterDto } from '../models/master.dto';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class MasterService {
  private readonly apiUrl = 'masters';

  constructor(private http: HttpClient) {}

  getAll(): Observable<MasterDto[]> {
    return this.http.get<MasterDto[]>(this.apiUrl);
  }

  getOne(id: number): Observable<MasterDto> {
    return this.http.get<MasterDto>(`${this.apiUrl}/${id}`);
  }

  create(master: MasterDto): Observable<MasterDto> {
    return this.http.post<MasterDto>(this.apiUrl, master);
  }

  update(id: number, master: MasterDto): Observable<MasterDto> {
    return this.http.put<MasterDto>(`${this.apiUrl}/${id}`, master);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
