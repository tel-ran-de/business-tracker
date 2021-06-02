import {Component, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModalConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-confirmation-modal',
  templateUrl: './delete-confirmation-modal.component.html',
  styleUrls: ['./delete-confirmation-modal.component.css']
})
export class DeleteConfirmationModalComponent implements OnInit {

  constructor(public modal: NgbActiveModal, private modalConfig: NgbModalConfig) {
    modalConfig.backdrop = 'static';
  }

  ngOnInit(): void {
  }

}
