import { Component, EventEmitter, Input, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OsebnoSpremljanjePostData, OsebnoSpremljanjeServiceService } from '../../../services/osebno-spremljanje-service.service';
import { OsebnoSpremljanje } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-osebno-spremljanje-post-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './osebno-spremljanje-post-form.component.html',
  styleUrl: './osebno-spremljanje-post-form.component.css'
})
export class OsebnoSpremljanjePostFormComponent {
  @Input() clanId!: number;
  @Output() submitSuccess = new EventEmitter<OsebnoSpremljanje>();

  constructor(private fb :  FormBuilder, private osebnoSpremljanjeService : OsebnoSpremljanjeServiceService) {}
  osebnoSpremljanjePostForm! : FormGroup;
  submitSuccessfull: boolean= false;

  ngOnChanges(changes : SimpleChanges){
    if(changes['clanId']){
      this.formInit();
      this.submitSuccessfull= false;
    }
  }


  formInit(){
    this.osebnoSpremljanjePostForm = this.fb.group({
      datum: [this.getDefaultDateTime(), [Validators.required]],
      naslov: ['', [Validators.required, Validators.maxLength(100)]],
      vsebina: ['']
    });
  }

  getDefaultDateTime(): string {
    const currentDate = new Date();
    return currentDate.toISOString().slice(0, 16); // Format it to 'YYYY-MM-DDTHH:MM' for datetime-local input
  }

  postOsebnoSpremljanje(){
    let postData = new OsebnoSpremljanjePostData();
    postData = this.osebnoSpremljanjePostForm.value;
    console.log(postData);
    postData.clanId = this.clanId;
    this.osebnoSpremljanjeService.postOsebnoSpremljanje(postData).subscribe(
      (response) => {
        this.submitSuccess.emit(response);
        this.submitSuccessfull = true;
      }
    )
  }
}
