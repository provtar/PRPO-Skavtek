import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Prisotnost } from '../../../services/data/user-data.service';
import { PrisotnostiService, TipPrisotnosti } from '../../../services/prisotnosti.service';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prisotnosti-put-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './prisotnosti-put-form.component.html',
  styleUrl: './prisotnosti-put-form.component.css'
})
export class PrisotnostiPutFormComponent {
  @Input() prisotnosti!: Prisotnost[]; //lahko je katerikoli izbor prisotnosti, ne nujno za isto skupino ali za isto srecanje
  @Output() submitSuccess = new EventEmitter<Prisotnost[]>();

  constructor(private prisotnostiService : PrisotnostiService, private fb : FormBuilder){}
  prisotnostiPutForm!: FormGroup;
  submitSuccessfull : boolean = false;
  tipiPrisotnosti = Object.values(TipPrisotnosti);

  ngOnChanges(changes : SimpleChanges) {
    if(changes['prisotnosti']){
      this.formInit();
    }
  }

  onSubmit(){
    let putData = this.prisotnostiPutForm.get('prisotnosti')?.value;
    console.log(putData);
    this.prisotnostiService.putPrisotnosti(putData).subscribe(
      (response) => {
        console.log("sub success", response);
        this.submitSuccess.emit(response);
      })
  }

  formInit(){
    this.prisotnostiPutForm = this.fb.group({
      prisotnosti: this.fb.array([])
    })
    this.prisotnosti.forEach((prisotnost, index) => {
      this.prisotnostiForm.push(this.createOnePrisotnostForm(prisotnost));
      console.log("index stima", this.prisotnosti.at(index)!.id === this.prisotnostiForm.at(index).value.id);
    });
  }

  get prisotnostiForm(){
    return this.prisotnostiPutForm.get('prisotnosti') as FormArray;
  }

  createOnePrisotnostForm(prisotnost: Prisotnost){
    return this.fb.group({
      id: [prisotnost.id, [Validators.required]],
      opomba: [prisotnost.opomba],
      prisotnost: [prisotnost.prisotnost, [Validators.required]]
    });
  }

}
