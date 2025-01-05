import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Skupina, UserDataService } from '../../../services/data/user-data.service';
import { ClaniSkupinePutFormComponent } from "../../form/clani-skupine-put-form/clani-skupine-put-form.component";
import { SkupinaPutFormComponent } from "../../form/skupina-put-form/skupina-put-form.component";
import { CommonModule } from '@angular/common';
import { SkupinaService } from '../../../services/skupina.service';
import { GlobalVarService } from '../../../services/data/global-var.service';

@Component({
  selector: 'app-skupina-put-modal',
  standalone: true,
  imports: [SkupinaPutFormComponent, CommonModule],
  templateUrl: './skupina-put-modal.component.html',
  styleUrl: './skupina-put-modal.component.css'
})
export class SkupinaPutModalComponent {
    @Input() skupina!: Skupina;
    @Output() posodobljenaSkupina: Skupina = new Skupina(); //no output, bo samo nazaj klicalo REST da dobi nove podatke, nolj varno, boljse to premisli, itak parent dobi podatke iz data service
    @Output() closeModal = new EventEmitter<void>();

    @ViewChild("skupinaPutForm") skupinaPutForm : SkupinaPutFormComponent | undefined;
    @ViewChild("claniSkupinePutForm") claniSkupinePutForm : ClaniSkupinePutFormComponent | undefined;

    constructor(private userData: UserDataService, private globalVar: GlobalVarService) {}
    isVisible = false;
    putClani = false;

    ngOnInit(): void {
    }

    onSkupinaSubmitSuccess(){
      if(this.skupinaPutForm?.posodobljenaSkupina){
        this.userData.posodobiSkupino(this.skupinaPutForm.posodobljenaSkupina);
        this.posodobljenaSkupina = this.skupinaPutForm.posodobljenaSkupina;
      }
      setTimeout(() => {
        this.close();
      }, this.globalVar.modalPutFadeTime);
    }

    onClaniSubmitSuccess(){
      setTimeout(() => {
          close();
        }, this.globalVar.modalPutFadeTime);
    }

    open() {
      this.isVisible = true;
      this.putClani = false;
    }

    close() {
      this.isVisible = false;
      this.closeModal.emit();
    }

    dodajClane() {
      this.putClani = true;
    }
}
