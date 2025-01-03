import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Povezava, Skupina, UserDataService } from '../../../services/user-data.service';
import { SkupinaPostData, SkupinaPutData, SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';
import { SkupinaPostFormComponent } from "../../form/skupina-post-form/skupina-post-form.component";

@Component({
  selector: 'app-skupina-post-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SkupinaPostFormComponent],
  templateUrl: './skupina-post-modal.component.html',
  styleUrl: './skupina-post-modal.component.css'
})
export class SkupinaPostModalComponent {
  isVisible = false;
  skupinaPosted = false;

  @Output() novaSkupina: Skupina | undefined = undefined;
  @Output() closeModal = new EventEmitter<void>();

  @ViewChild('skupinaPostForm') skupinaPostForm : SkupinaPostFormComponent | undefined;

  constructor(private fb: FormBuilder, private skupinaService: SkupinaService, private userData: UserDataService) {}

  ngOnInit(): void {
  }

  onSkupinaPostSuccess(){
    if(this.skupinaPostForm?.novaSkupina){
      this.userData.dodajSkupino(this.skupinaPostForm.novaSkupina);
      this.skupinaPosted = true;
    }
  }

  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.closeModal.emit();
  }
}
