import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DetailService } from '../../data/services/detail.service';
import { MasterDto } from '../../data/models/master.dto';
import { DetailDto } from '../../data/models/detail.dto';
import { DetailForm } from '../detail-form/detail-form.component';
import { MasterService } from '../../data/services/master.service';

@Component({
  selector: 'app-detail-list',
  standalone: true,
  imports: [CommonModule, DetailForm],
  templateUrl: './detail-list.component.html',
})
export class DetailList {
  @Input() master!: MasterDto;
  @Output() changed = new EventEmitter<void>();
  @ViewChild('detailForm') detailForm!: DetailForm;

  constructor(private detailService: DetailService, private masterService: MasterService) {}

  editDetail(detail: DetailDto) {
    this.detailForm.edit(detail);
  }

  deleteDetail(detail: DetailDto) {
    if (!confirm('Удалить Detail?')) return;

    this.detailService.delete(this.master.id!, detail.id!).subscribe(() => {
      this.reloadMaster();
    });
  }

  onDetailAdded() {
    this.reloadMaster();
  }

  private reloadMaster() {
    this.masterService.getOne(this.master.id!).subscribe((updatedMaster) => {
      this.master = updatedMaster;
      this.changed.emit();
    });
  }
}
