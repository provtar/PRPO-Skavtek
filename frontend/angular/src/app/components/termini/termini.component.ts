import { Component, Input, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TerminiServiceService } from '../../services/termini-service.service';
import { Termin } from '../../services/data/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-termini',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './termini.component.html',
  styleUrl: './termini.component.css'
})
export class TerminiComponent {
  @Input() clanId!: number;



  constructor(private fb : FormBuilder, private terminService : TerminiServiceService){}
  getForm! : FormGroup;
  putForm! : FormGroup;
  termini! : Termin[];
  initialized = false;
  uredi = false;
  values = ['Prost', 'Zaseden', 'Drugo'];

  ngOnChanges(changes : SimpleChanges){
    if(changes['clanId']){
      this.getFormInit();
    }
  }

  urediJih(){
    this.uredi = true;
  }

  neuredi(){
    this.uredi = false;
  }

  getFormInit(){
      this.getForm = this.fb.group({
        datumOd: ['', Validators.required],  // Start Date
        datumDo: ['', Validators.required],    // End Date
      });
  }

  putFormInit(){
    this.putForm = this.fb.group({
      termini: this.fb.array([]), // Initialize empty FormArray
    });
    this.termini.forEach(element => {
      this.formArray.push(this.createTermin(element));
    });
  }

  get formArray(): FormArray {
    return this.putForm.get('termini') as FormArray;
  }

  removeTermin(index: number){
    this.formArray.removeAt(index);
  }

  createTermin(termin : Termin){
    return this.fb.group({
      clanId: [this.clanId],
      datumOd: [termin.datumOd, Validators.required],
      datumDo: [termin.datumDo, Validators.required],
      tip: [termin.tip, Validators.required]
    })
  }

  getSubmit(){
    this.terminService.getTermin(this.getForm.get('datumOd')?.value, this.getForm.get('datumDo')?.value, this.clanId).subscribe(
      (res) => {
        this.termini = res.sort();
        this.initialized = true;
      }
    )
  }

  putSubmit(){
    this.terminService.postTermini( this.clanId, this.getForm.get('datumOd')?.value, this.getForm.get('datumDo')?.value, this.formArray.value).subscribe(
      (res) => { this.termini = res.sort()}
    )
  }
}
