import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClanDataService } from '../../../services/clan-data.service';
import { Clan, UserDataService } from '../../../services/user-data.service';

@Component({
  selector: 'app-clan-post-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clan-post-modal.component.html',
  styleUrl: './clan-post-modal.component.css'
})
export class ClanPostModalComponent {
  isVisible = false;
  clanPostForm!: FormGroup;
  novClan: Clan | undefined = undefined;

  constructor(private fb: FormBuilder, private clanDataService: ClanDataService, private userData: UserDataService) {}

  ngOnInit(): void {
    this.clanPostForm = this.fb.group({
      ime: ['', [Validators.required, Validators.minLength(2)]],
      priimek: ['', [Validators.required, Validators.minLength(2)]],
      steg: [''],
      skavtskoIme: ['']
    });
  }

  @Output() closeModal = new EventEmitter<void>();

  open() {
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.closeModal.emit();
  }

  clanPostSubmit() {
    if(localStorage.getItem('user')){
      const user : Clan = JSON.parse(localStorage.getItem('user') as string);
      this.clanDataService.postClan(this.clanPostForm.value, user.id).subscribe(
        (response) => {
          this.userData.dodajVarovanca(response);
          this.novClan = response;
        }
      );
    }
  }
}
