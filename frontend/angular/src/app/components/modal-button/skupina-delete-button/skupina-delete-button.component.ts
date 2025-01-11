import { Component, Input } from '@angular/core';
import { UserDataService } from '../../../services/data/user-data.service';
import { SkupinaService } from '../../../services/skupina.service';

@Component({
  selector: 'app-skupina-delete-button',
  standalone: true,
  imports: [],
  templateUrl: './skupina-delete-button.component.html',
  styleUrl: './skupina-delete-button.component.css'
})
export class SkupinaDeleteButtonComponent {

  constructor(private userData : UserDataService, private skupinaService: SkupinaService) {}

  @Input() skupinaId!: number;

  public odstraniSkupino(){
    if(this.skupinaId){
      this.skupinaService.deleteSkupina(this.skupinaId).subscribe(
        (response) => {
          this.userData.odstraniSkupino(this.skupinaId);
          console.log("Delete skupine dela: skupina delete button")
        }
      )
    }
  }
}
