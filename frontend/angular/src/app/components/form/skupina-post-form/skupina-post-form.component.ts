import { Component, EventEmitter, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Povezava, Skupina} from '../../../services/data/user-data.service';
import { SkupinaPostData, SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-skupina-post-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './skupina-post-form.component.html',
  styleUrl: './skupina-post-form.component.css'
})
export class SkupinaPostFormComponent {
  skupinaPostForm!: FormGroup;
  @Output() novaSkupina: Skupina | undefined = undefined;
  @Output() skupinaPostSuccess = new EventEmitter<void>();

  constructor(private fb: FormBuilder, private skupinaService: SkupinaService) {}

  ngOnInit(): void {
    this.skupinaPostForm = this.fb.group({
      ime: ['', [Validators.required, Validators.minLength(2)]],
      opis: [''],
      povezave: this.fb.array([]),
    });
  }

  createPovezava() {
    return this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      link: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  povezave() : FormArray{
    return this.skupinaPostForm.get('povezave') as FormArray;
  }

  addPovezava() {
    this.povezave().push(this.createPovezava());
  }

  removePovezava(index: number){
    this.povezave().removeAt(index);
  }

  skupinaPostSubmit() {
    if(localStorage.getItem('user')){
      const povezaveCiste = this.povezave().value.filter((link : Povezava) => {
        return link && link.name && link.link;
      });
      let postData : SkupinaPostData = new SkupinaPostData();
      postData.ime = this.skupinaPostForm.value.ime;
      postData.opis = this.skupinaPostForm.value.opis;
      postData.povezave = povezaveCiste;

      this.skupinaService.postSkupina(postData).subscribe((response) => {
        this.novaSkupina = response;
        this.skupinaPostSuccess.emit();
      });
    }
  }
}
