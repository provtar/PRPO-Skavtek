
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Povezava, Skupina} from '../../../services/data/user-data.service';
import { SkupinaPostData, SkupinaPutData, SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-skupina-put-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './skupina-put-form.component.html',
  styleUrl: './skupina-put-form.component.css'
})
export class SkupinaPutFormComponent {
  @Input() skupina!: Skupina; //spreminjaj jo preko polj ne reference!!!
  @Output() posodobljenaSkupina: Skupina = new Skupina; //po submit success ni vec null, boljse da ga event emitta kot ta dualnost
  @Output() submitSuccess = new EventEmitter<void>(); //ti pove, da je form bil poslan

  constructor(private fb: FormBuilder, private skupinaService: SkupinaService) {}
  skupinaPutForm!: FormGroup;
  submitSuccessfull: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['skupina']){
      this.initForm();
    }
  }

  initForm(){
    this.submitSuccessfull = false;
    this.posodobljenaSkupina = new Skupina();
    this.skupinaPutForm = this.fb.group({
      ime: [this.skupina.ime, [Validators.required, Validators.minLength(2)]],
      opis: [this.skupina.opis],
      povezave: this.fb.array([]),
    });
    this.initPovezave();
  }

  povezave() : FormArray{
    return this.skupinaPutForm.get('povezave') as FormArray;
  }

  addPovezava(name: string = '', link: string = '') {
    this.povezave().push(this.createPovezava());
  }

  removePovezava(index: number){
    this.povezave().removeAt(index);
  }

  private createPovezava(name: string = '', link: string = '') {
    return this.fb.group({
      name: [name, [Validators.required, Validators.minLength(2)]],
      link: [link, [Validators.required, Validators.minLength(5)]]
    });
  }

  private initPovezave(){
    if(this.skupina.povezave){
      this.skupina.povezave.forEach(povezava => {
        this.addPovezava(povezava.name, povezava.link);
      });
    }
  }

  skupinaPutSubmit() {
    const povezaveCiste = this.povezave().value.filter((link : Povezava) => {
      return link && link.name && link.link;
    });
    let putData : SkupinaPutData = new SkupinaPutData();
    putData.id = this.skupina.id;
    putData.ime = this.skupinaPutForm.value.ime;
    if(this.skupinaPutForm.value.opis)putData.opis = this.skupinaPutForm.value.opis;
      else putData.opis = null;
    putData.povezave = povezaveCiste;

    this.skupinaService.putSkupina(putData).subscribe((response) => {
      this.posodobljenaSkupina = response;
      this.submitSuccessfull = true;
      this.submitSuccess.emit();
    });
  }
}
