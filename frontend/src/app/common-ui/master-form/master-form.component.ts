import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MasterService } from '../../data/services/master.service';
import { MasterDto } from '../../data/models/master.dto';

@Component({
  selector: 'app-master-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './master-form.component.html',
})
export class MasterForm {
  @Output() saved = new EventEmitter<void>();

  master: MasterDto = { number: '', date: '', sum: 0, note: '' };
  editingId?: number;

  constructor(private masterService: MasterService) {}

  save() {
    if (!this.master.number) return alert('Введите номер!');

    const action = this.editingId
      ? this.masterService.update(this.editingId, this.master)
      : this.masterService.create(this.master);

    action.subscribe(() => {
      this.reset();
      this.saved.emit();
    });
  }

  edit(master: MasterDto) {
    this.master = {
      ...master,
      date: this.formatDateForInput(master.date),
    };
    this.editingId = master.id;
  }

  private formatDateForInput(date: any): string {
    if (!date) return '';

    // Если дата это массив [год, месяц, день] - формат Java LocalDate
    if (Array.isArray(date) && date.length === 3) {
      const [year, month, day] = date;
      const monthStr = String(month).padStart(2, '0');
      const dayStr = String(day).padStart(2, '0');
      return `${year}-${monthStr}-${dayStr}`;
    }
    return '';
  }

  reset() {
    this.master = { number: '', date: '', sum: 0, note: '' };
    this.editingId = undefined;
  }
}
