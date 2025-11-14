import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MasterService } from '../../data/services/master.service';
import { MasterDto } from '../../data/models/master.dto';
import { MasterForm } from '../master-form/master-form.component';
import { DetailList } from '../detail-list/detail-list.component';
import { DateFormatPipe } from '../../core/pipes/date-format';

@Component({
  selector: 'app-master-list',
  standalone: true,
  imports: [CommonModule, MasterForm, DetailList, DateFormatPipe],
  templateUrl: './master-list.component.html',
  styleUrl: './master-list.component.scss',
})
export class MasterList implements OnInit {
  masters: MasterDto[] = [];
  loading = true;
  selectedMaster?: MasterDto;

  @ViewChild('masterForm') masterForm!: MasterForm;

  constructor(private masterService: MasterService) {}

  ngOnInit() {
    this.loadMasters();
  }

  loadMasters() {
    this.loading = true;
    this.masterService.getAll().subscribe((data) => {
      this.masters = data;
      this.loading = false;
    });
  }

  selectMaster(master: MasterDto) {
    this.selectedMaster = master;
  }

  editMaster(master: MasterDto) {
    this.masterForm.edit(master);
    this.selectedMaster = undefined;
  }

  deleteMaster(id: number) {
    if (confirm('Удалить Master?')) {
      this.masterService.delete(id).subscribe(() => this.loadMasters());
    }
  }
}
