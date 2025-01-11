import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClanDataService } from '../../../services/clan.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-clan-put-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clan-put-form.component.html',
  styleUrl: './clan-put-form.component.css'
})
export class ClanPutFormComponent {
  @Input() clan! : Clan;
  @Output() submitSuccess = new EventEmitter<Clan>();

  clanPutForm!: FormGroup;
  submitSuccessfull : boolean = false;

  constructor(private fb: FormBuilder, private clanDataService: ClanDataService,
    private userData: UserDataService) {}

  ngOnChanges(changes : SimpleChanges){
    if(changes['clan']){
      this.initForm();
    }
  }

  initForm() {
    if(this.clan){
      this.clanPutForm = this.fb.group({
        id : [this.clan.id],
        ime: [this.clan.ime, [Validators.required, Validators.minLength(2)]],
        priimek: [this.clan.priimek, [Validators.required, Validators.minLength(2)]],
        steg: [this.clan.steg],
        skavtskoIme: [this.clan.skavtskoIme]
      });
    }
  }

  clanPutSubmit() {
    if(localStorage.getItem('user')){
      this.clanDataService.putClan(this.clanPutForm.value).subscribe(
        (response) => {
          this.submitSuccess.emit(response);
        }
      );
    }
  }
}
