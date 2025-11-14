import { DetailDto } from './detail.dto';

export interface MasterDto {
  id?: number;
  number: string;
  date: string | number[];
  sum: number;
  note?: string;
  details?: DetailDto[];
}
