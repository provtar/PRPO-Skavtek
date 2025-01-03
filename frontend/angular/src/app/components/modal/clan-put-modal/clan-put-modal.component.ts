import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Clan, UserDataService } from '../../../services/user-data.service';
import { ClanDataService, ClanPutData } from '../../../services/clan.service';
import { GlobalVarService } from '../../../services/global-var.service';

@Component({
  selector: 'app-clan-put-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clan-put-modal.component.html',
  styleUrl: './clan-put-modal.component.css'
})
export class ClanPutModalComponent {
  isVisible = false;
  clanToPut: Clan | undefined = undefined;
  clanPutForm!: FormGroup;
  uspelo = false;

  constructor(private fb: FormBuilder, private clanDataService: ClanDataService,
    private userData: UserDataService, private globalVar: GlobalVarService) {}

  ngOnInit(): void {this.clanPutForm = this.fb.group({
    id : [0],
    ime: ['', [Validators.required, Validators.minLength(2)]],
    priimek: ['', [Validators.required, Validators.minLength(2)]],
    steg: [''],
    skavtskoIme: ['']
  });
  }

  @Output() closeModal = new EventEmitter<void>();

  open(data: Clan) {
    if(data && data.id && data.ime && data.priimek){
      this.clanToPut = data;
      this.clanPutForm = this.fb.group({
        id : [this.clanToPut.id],
        ime: [this.clanToPut.ime, [Validators.required, Validators.minLength(2)]],
        priimek: [this.clanToPut.priimek, [Validators.required, Validators.minLength(2)]],
        steg: [this.clanToPut.steg],
        skavtskoIme: [this.clanToPut.skavtskoIme]
      });
      this.isVisible = true;
    }
  }

  close() {
    this.isVisible = false;
    this.uspelo = false;
    this.clanToPut = undefined;
    this.closeModal.emit();
  }

  clanPutSubmit() {
    if(localStorage.getItem('user')){
      this.clanDataService.putClan(this.clanPutForm.value).subscribe(
        (response) => {
          this.userData.posodobiVarovanca(response);
          this.clanToPut = response;
          this.uspelo = true;
          setTimeout(() => this.close(), this.globalVar.modalPutFadeTime);
        }
      );
    }
  }
}
