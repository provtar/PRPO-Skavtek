import { Component, EventEmitter, Input, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OsebnoSpremljanjePostData, OsebnoSpremljanjePutData, OsebnoSpremljanjeServiceService } from '../../../services/osebno-spremljanje-service.service';
import { OsebnoSpremljanje } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-osebno-spremljanje-put-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './osebno-spremljanje-put-form.component.html',
  styleUrl: './osebno-spremljanje-put-form.component.css'
})
export class OsebnoSpremljanjePostFormComponent {
  @Input() osebnoSpremljanje!: OsebnoSpremljanje;
  @Output() submitSuccess = new EventEmitter<OsebnoSpremljanje>();

  constructor(private fb :  FormBuilder, private osebnoSpremljanjeService : OsebnoSpremljanjeServiceService) {}
  osebnoSpremljanjePutForm! : FormGroup;
  submitSuccessfull: boolean= false;

  ngOnChanges(changes : SimpleChanges){
    if(changes['osebnoSpremljanje']){
      this.formInit();
      this.submitSuccessfull= false;
    }
  }


  formInit(){
    this.osebnoSpremljanjePutForm = this.fb.group({
      datum: [this.getShortDateTime(this.osebnoSpremljanje.datum), [Validators.required]],
      naslov: [this.osebnoSpremljanje.naslov, [Validators.required]],
      vsebina: [this.osebnoSpremljanje.vsebina]
    });
  }

  getShortDateTime(date : Date): string {
    return date.toISOString().slice(0, 16);
  }

  submitForm(){
    let putData = new OsebnoSpremljanjePutData();
    putData = this.osebnoSpremljanjePutForm.value;
    console.log(putData);
    putData.id = this.osebnoSpremljanje.id;
    this.osebnoSpremljanjeService.putOsebnoSpremljanje(putData).subscribe(
      (response) => {
        this.submitSuccess.emit(response);
        this.submitSuccessfull = true;
      }
    )
  }
}
