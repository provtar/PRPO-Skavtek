import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Povezava, Skupina, UserDataService } from '../../../services/data/user-data.service';
import { SkupinaPostData, SkupinaPutData, SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';
import { SkupinaPostFormComponent } from "../../form/skupina-post-form/skupina-post-form.component";
import { ClaniSkupinePutFormComponent } from "../../form/clani-skupine-put-form/clani-skupine-put-form.component";
import { GlobalVarService } from '../../../services/data/global-var.service';

@Component({
  selector: 'app-skupina-post-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SkupinaPostFormComponent, ClaniSkupinePutFormComponent],
  templateUrl: './skupina-post-modal.component.html',
  styleUrl: './skupina-post-modal.component.css'
})
export class SkupinaPostModalComponent {
  isVisible = false;
  skupinaPosted = false;
  claniSkupinePosted = false;
  novaSkupina: Skupina | undefined;

  @Output() postSuccess = new EventEmitter<Skupina>();
  @Output() closeModal = new EventEmitter<void>();

  @ViewChild("skupinaPostForm") skupinaPostForm : SkupinaPostFormComponent | undefined;
  @ViewChild("claniSkupinePutForm") claniSkupinePutForm : ClaniSkupinePutFormComponent | undefined;

  constructor(private fb: FormBuilder, private skupinaService: SkupinaService, private userData: UserDataService, private globalVar : GlobalVarService) {}

  ngOnInit(): void {
  }

  onSkupinaPostSuccess(novaSkupina : Skupina){
    this.skupinaPosted = true;
    this.novaSkupina = novaSkupina;
  }

  onClaniPutSuccess(){
    this.claniSkupinePosted = true;
    this.postSuccess.emit(this.novaSkupina);
    setTimeout(() => {
      this.close();
    }, this.globalVar.modalPutFadeTime);
  }

  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.skupinaPosted = false;
    this.closeModal.emit();
  }
}
