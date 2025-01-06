import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClanDataService } from '../../../services/clan.service';
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { ClanPostFormComponent } from "../../form/clan-post-form/clan-post-form.component";

@Component({
  selector: 'app-clan-post-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ClanPostFormComponent],
  templateUrl: './clan-post-modal.component.html',
  styleUrl: './clan-post-modal.component.css'
})
export class ClanPostModalComponent {
  @Input() masterId! :number;
  @Output() closeModal = new EventEmitter<void>();
  @Output() postSuccess = new EventEmitter<Clan>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible = false;


  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.closeModal.emit();
  }

  onPostSuccess(novClan : Clan){
    this.postSuccess.emit(novClan);
    setTimeout(() => {
      this.close();
    }, this.globalVar.modalPutFadeTime);
  }
}
