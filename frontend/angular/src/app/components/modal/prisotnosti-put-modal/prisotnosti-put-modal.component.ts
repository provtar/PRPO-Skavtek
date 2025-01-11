import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Prisotnost } from '../../../services/data/user-data.service';
import { GlobalVarService } from '../../../services/data/global-var.service';
import { CommonModule } from '@angular/common';
import { PrisotnostiPutFormComponent } from "../../form/prisotnosti-put-form/prisotnosti-put-form.component";

@Component({
  selector: 'app-prisotnosti-put-modal',
  standalone: true,
  imports: [CommonModule, PrisotnostiPutFormComponent],
  templateUrl: './prisotnosti-put-modal.component.html',
  styleUrl: './prisotnosti-put-modal.component.css'
})
export class PrisotnostiPutModalComponent {
  @Input() prisotnosti!: Prisotnost[];
  @Output() putSuccess = new EventEmitter<Prisotnost[]>();
  @Output() closeModal = new EventEmitter<void>();

  constructor(private globalVar : GlobalVarService) {}
  isVisible : boolean = false;

  onFormSuccess(posodobljenePrisotnosti: Prisotnost[]){
    this.putSuccess.emit(posodobljenePrisotnosti);
    setTimeout(() => {
      this.close();
    }, this.globalVar.modalPutFadeTime);
  }

  open() {
    this.isVisible = true;
  }

  close(){
    this.isVisible = false;
    this.closeModal.emit();
  }

}
