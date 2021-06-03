import {Component, Input, OnDestroy} from '@angular/core';
import {KpiService} from '../../../../../serivce/kpi.service';
import {Subscription} from 'rxjs';
import {KpiToDisplay} from '../../../../../models/kpi/kpi-to-display';
import {DeleteConfirmationModalComponent} from '../../delete-confirmation-modal/delete-confirmation-modal.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-kpi-items',
  templateUrl: './kpi-items.component.html',
  styleUrls: ['./kpi-items.component.css']
})
export class KpiItemsComponent implements OnDestroy {
  @Input()
  kpis: KpiToDisplay[];
  private subscriptions: Subscription[] = [];

  @Input()
  maxHeightElt: number;
  @Input()
  mileStoneId: number;

  openForm = false;
  removeId = 0;

  constructor(private kpiService: KpiService, private modal: NgbModal) {
  }

  onClickRemoveKpi(kpi: KpiToDisplay): void {
    const removeConfirmModal = this.modal.open(DeleteConfirmationModalComponent);
    removeConfirmModal.result.then(value => {
        if (value === 'ok') {
          this.removeId = kpi.id;
          const removeKpiSub = this.kpiService.removeById(kpi.id)
            .subscribe(() => {
              const removedKpiIndex = this.kpis.indexOf(kpi);
              this.kpis.splice(removedKpiIndex, 1);
              this.kpiService.kpiDeleted$.next(kpi);
              this.removeId = 0;
            }, error => console.error(error));

          this.subscriptions.push(removeKpiSub);
        }
      }
    );
  }

  kpiAdded($event: KpiToDisplay): void {
    this.kpis.push($event);
    this.openForm = false;
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
