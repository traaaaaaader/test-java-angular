import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DetailService } from '../../data/services/detail.service';
import { DetailDto } from '../../data/models/detail.dto';

@Component({
  selector: 'app-detail-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './detail-form.component.html',
})
export class DetailForm {
  @Input() masterId!: number;
  @Output() added = new EventEmitter<void>();

  detail: DetailDto = { name: '', amount: 0 };
  editingId?: number;

  constructor(private detailService: DetailService) {}

  addDetail() {
    if (!this.detail.name) return alert('Введите название детали!');

    const action = this.editingId
      ? this.detailService.update(this.masterId, this.editingId, this.detail)
      : this.detailService.add(this.masterId, this.detail);

    action.subscribe(() => {
      this.reset();
      this.added.emit();
    });
  }

  edit(detail: DetailDto) {
    this.detail = { ...detail };
    this.editingId = detail.id;
  }

  reset() {
    this.detail = { name: '', amount: 0 };
    this.editingId = undefined;
  }
}
