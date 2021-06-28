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
  removeKpi: string = '';

  constructor(private kpiService: KpiService, private modal: NgbModal) {
  }

  onClickRemoveKpi(kpi: KpiToDisplay): void {
    const removeConfirmModal = this.modal.open(DeleteConfirmationModalComponent);
    removeConfirmModal.result.then(value => {
        if (value === 'ok') {
          this.removeKpi = kpi.kpi;
          const removeKpiIndex = this.kpis.indexOf(kpi);

          if (removeKpiIndex === -1) {
            console.log("Index not found");
            return;
          } else {
            const removeKpiSub = this.kpiService.removeKpiByKpi(this.mileStoneId, removeKpiIndex)
              .subscribe(() => {
                this.kpis.splice(removeKpiIndex, 1);
                this.kpiService.kpiDeleted$.next(kpi);
                this.removeKpi = '';
              }, error => console.error(error));

            this.subscriptions.push(removeKpiSub);
          }

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
