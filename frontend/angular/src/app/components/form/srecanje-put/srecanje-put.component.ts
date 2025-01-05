import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Srecanje } from '../../../services/data/user-data.service';
import { SrecanjaService, SrecanjePostData, SrecanjePutData } from '../../../services/srecanja.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-srecanje-put',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './srecanje-put.component.html',
  styleUrl: './srecanje-put.component.css'
})
export class SrecanjePutComponent {
  @Input() srecanje!: Srecanje;
  @Output() submitSuccess = new EventEmitter<Srecanje>();

  constructor(private srecanjeService: SrecanjaService, private fb: FormBuilder){}
  srecanjePutForm!: FormGroup;
  submitSuccessfull: boolean = false;

  ngOnChanges(changes: SimpleChanges){
    if(changes['skupinaId']){
      this.initForm();
    }
  }

  initForm(){
    this.srecanjePutForm = this.fb.group({
      ime: [this.srecanje.ime, Validators.required, Validators.minLength(2)],
      datumOd: [this.srecanje.datumOd.toDateString(), Validators.required],
      uraOd: [this.srecanje.datumOd.toTimeString(), Validators.required],
      datumDo: [this.srecanje.datumDo.toDateString, Validators.required],
      uraDo: [this.srecanje.datumDo.toTimeString(), Validators.required],
      kraj: [this.srecanje.kraj],
      opis: [this.srecanje.opis],
    })
  }


  submitForm(){
    if (this.srecanjePutForm.valid) {
      let formData = this.srecanjePutForm.value;
      let putData = new SrecanjePutData(this.srecanje.id, this.srecanje.idSkupine);

      putData.ime = formData.ime;
      putData.datumOd = new Date(`${formData.datumOd}T${formData.uraOd}`);
      putData.datumDo = new Date(`${formData.datumDo}T${formData.uraDo}`);
      if(formData.opis)putData.opis = formData.opis;
      else putData.opis = null;
      if(formData.kraj)putData.kraj = formData.kraj;
      else formData.kraj = null;

      this.srecanjeService.postSrecanje(putData).subscribe((response) => {
        this.submitSuccess.emit(response);
        this.submitSuccessfull = true;
      });
    }
  }
}
