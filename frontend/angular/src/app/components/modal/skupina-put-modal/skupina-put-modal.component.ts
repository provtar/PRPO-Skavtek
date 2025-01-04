import { Component, Input } from '@angular/core';
import { Skupina } from '../../../services/data/user-data.service';
import { ClaniSkupinePutFormComponent } from "../../form/clani-skupine-put-form/clani-skupine-put-form.component";

@Component({
  selector: 'app-skupina-put-modal',
  standalone: true,
  imports: [ClaniSkupinePutFormComponent],
  templateUrl: './skupina-put-modal.component.html',
  styleUrl: './skupina-put-modal.component.css'
})
export class SkupinaPutModalComponent {
  @Input() skupina!: Skupina;
}
