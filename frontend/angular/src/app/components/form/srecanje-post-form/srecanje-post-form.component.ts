import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Srecanje } from '../../../services/data/user-data.service';
import { SrecanjaService, SrecanjePostData } from '../../../services/srecanja.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-srecanje-post-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './srecanje-post-form.component.html',
  styleUrl: './srecanje-post-form.component.css'
})
export class SrecanjePostFormComponent {
    @Input() skupinaId!: number;
    @Output() submitSuccess = new EventEmitter<Srecanje>();

    constructor(private srecanjeService: SrecanjaService, private fb: FormBuilder){}
    srecanjePostForm!: FormGroup;
    submitSuccessfull: boolean = false;

    ngOnChanges(changes: SimpleChanges){
      if(changes['skupinaId']){
        this.initForm();
      }
    }

    initForm(){
      this.srecanjePostForm = this.fb.group({
        ime: ['', [Validators.required, Validators.minLength(2)]],
        datumOd: ['', [Validators.required]],
        uraOd: ['', [Validators.required]],
        datumDo: ['',[ Validators.required]],
        uraDo: ['',[ Validators.required]],
        kraj: [''],
        opis: [''],
      })
    }


    submitForm(){
      if (this.srecanjePostForm.valid) {
        let formData = this.srecanjePostForm.value;
        let postData = new SrecanjePostData(this.skupinaId);

        postData.ime = formData.ime;
        postData.datumOd = new Date(`${formData.datumOd}T${formData.uraOd}`);
        postData.datumDo = new Date(`${formData.datumDo}T${formData.uraDo}`);
        if(formData.opis)postData.opis = formData.opis;
        else postData.opis = null;
        if(formData.kraj)postData.kraj = formData.kraj;
        else formData.kraj = null;

        console.log('Form Data:', formData);
        console.log('Converted DatumOd:', postData.datumOd);
        console.log('Converted DatumDo:', postData.datumDo);

        this.srecanjeService.postSrecanje(postData).subscribe((response) => {
          this.submitSuccess.emit(response);
          this.submitSuccessfull = true;
        });
      }
    }
}
