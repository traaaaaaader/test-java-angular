import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat',
  standalone: true,
})
export class DateFormatPipe implements PipeTransform {
  transform(date: string | number[]): string {
    if (!date) return '';

    if (Array.isArray(date) && date.length === 3) {
      const [year, month, day] = date;
      const monthStr = String(month).padStart(2, '0');
      const dayStr = String(day).padStart(2, '0');
      return `${dayStr}.${monthStr}.${year}`;
    }

    if (typeof date === 'string' && date.includes('-')) {
      const [year, month, day] = date.split('-');
      return `${day}.${month}.${year}`;
    }

    return String(date);
  }
}
