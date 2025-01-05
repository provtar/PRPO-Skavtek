import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Povezava, Skupina, UserDataService } from '../../../services/data/user-data.service';
import { SkupinaPostData, SkupinaPutData, SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';
import { SkupinaPostFormComponent } from "../../form/skupina-post-form/skupina-post-form.component";
import { ClaniSkupinePutFormComponent } from "../../form/clani-skupine-put-form/clani-skupine-put-form.component";

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
  skupinaId!: number;

  @Output() novaSkupina: Skupina | undefined = undefined;
  @Output() closeModal = new EventEmitter<void>();

  @ViewChild("skupinaPostForm") skupinaPostForm : SkupinaPostFormComponent | undefined;
  @ViewChild("claniSkupinePutForm") claniSkupinePutForm : ClaniSkupinePutFormComponent | undefined;

  constructor(private fb: FormBuilder, private skupinaService: SkupinaService, private userData: UserDataService) {}

  ngOnInit(): void {
  }

  onSkupinaPostSuccess(){
    if(this.skupinaPostForm?.novaSkupina){
      this.userData.dodajSkupino(this.skupinaPostForm.novaSkupina);
      this.novaSkupina = this.skupinaPostForm.novaSkupina;
      this.skupinaId = this.skupinaPostForm.novaSkupina?.id as number;
      this.skupinaPosted = true;
    }
  }

  onClaniPutSuccess(){
    this.claniSkupinePosted = true;
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
