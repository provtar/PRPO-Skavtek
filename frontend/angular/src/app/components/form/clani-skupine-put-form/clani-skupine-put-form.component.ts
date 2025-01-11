import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { SkupineDataService } from '../../../services/data/skupine-data.service';
import { SkupinaService } from '../../../services/skupina.service';
import { Clan, ClanSkupine, UserDataService } from '../../../services/data/user-data.service';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClanDataService } from '../../../services/clan.service';

@Component({
  selector: 'app-clani-skupine-put-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clani-skupine-put-form.component.html',
  styleUrl: './clani-skupine-put-form.component.css'
})
export class ClaniSkupinePutFormComponent {

  constructor(private skupinaData: SkupineDataService, private skupinaService: SkupinaService, private userData: UserDataService,
    private fb: FormBuilder, private clanService : ClanDataService ) {}

  @Input() skupinaId!: number;
  @Output() submitSuccess = new EventEmitter<ClanSkupine[]>();
  clani: Clan[] = [];
  claniPutForm!: FormGroup;
  initialized: boolean = false;
  submitted: boolean = false;


  ngOnChanges(changes: SimpleChanges){
    if(changes['skupinaId']){
      this.claniPutForm = this.fb.group({
        izbraniClani: this.fb.array([]),
      });
      this.initForm();
    }
  }

  initForm(){
    this.submitted = false;
    this.claniPutForm = this.fb.group({
      izbraniClani: this.fb.array([]),
    });
    if(this.skupinaId) {//grdo, ampak forkjoin se slabo obnasa z subscribe ma BehaviorSubject
      this.skupinaService.getClaniSkupine(this.skupinaId).subscribe(
        (claniSkupine) => {
          this.clanService.getVarovanci(this.userData.user.id).subscribe(
            (varovanci) => {
                this.clani = varovanci.filter(
                varovanec => {
                  return !claniSkupine.some(clan => clan.clanId === varovanec.id);
                });
                this.initializeCheckboxes();
              });
          }
        )
    }
  }

  onSubmit() {
    const selectedValues = this.selectedOptions.controls.filter((control: any) => {return control.get('checked').value});
    const selectedIds: number[] = selectedValues.map((control: any) => control.get('id').value as number);

    selectedIds.push(this.userData.user.id);
    this.skupinaService.putClaneSkupine(this.skupinaId, selectedIds).subscribe(
      (response) =>{
        this.submitted = true;
        this.submitSuccess.emit(response);
      }
    );
  }

  onCheckboxChange(event: any, id: number) {
    const selectedOptions = this.selectedOptions;
    if (event.target.checked) {
      const checkbox = selectedOptions.controls.find((control: any) => control.value.id === id);
      if(checkbox?.get('id'))console.log(`checkbox: ${checkbox?.get('id')!.value}`);
      if (checkbox) {
        checkbox.value.checked = true;
      }
    }
    else{
      const checkbox = selectedOptions.controls.find((control: any) => control.value.id === id);
      console.log(`checkbox onchek: ${checkbox?.get('id')?.value}`);
      if (checkbox) {
        checkbox.value.checked = false;
      }
    }
  }

  createGroup(id: number){
    return this.fb.group({
      checked: [false],
      id: [id],
    });
  }

  get selectedOptions(): FormArray {
    return this.claniPutForm.get('izbraniClani') as FormArray;
  }

  initializeCheckboxes() {
    this.clani.forEach(clan => {
      const checkboxGroup = this.createGroup(clan.id);
      this.selectedOptions.push(checkboxGroup);
    });
  }
}
