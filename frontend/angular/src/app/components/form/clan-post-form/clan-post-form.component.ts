import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Clan } from '../../../services/data/user-data.service';
import { ClanDataService } from '../../../services/clan.service';

@Component({
  selector: 'app-clan-post-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clan-post-form.component.html',
  styleUrl: './clan-post-form.component.css'
})
export class ClanPostFormComponent {

  @Input() masterId! : number;
  @Output() submitSuccess = new EventEmitter<Clan>();

  constructor(private fb: FormBuilder, private clanDataService: ClanDataService) {}
  clanPostForm!: FormGroup;
  submitSuccessfull : boolean = false;

  ngOnChanges(changes : SimpleChanges){
    if(changes['masterId']){
      this.formInit();
    }
  }

  formInit(){
    this.clanPostForm = this.fb.group({
      ime: ['', [Validators.required, Validators.minLength(2)]],
      priimek: ['', [Validators.required, Validators.minLength(2)]],
      steg: [''],
      skavtskoIme: ['']
    });
  }

  clanPostSubmit() {
    if(localStorage.getItem('user')){
      this.clanDataService.postClan(this.clanPostForm.value, this.masterId).subscribe(
        (response) => {
          this.submitSuccess.emit(response);
          this.submitSuccessfull = true;
        }
      );
    }
  }
}
