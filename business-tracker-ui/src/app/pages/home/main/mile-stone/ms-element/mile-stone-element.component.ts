import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MileStoneToDisplay} from "../../../../../models/mile-stone/mile-stone-to-display";
import {KpiToDisplay} from "../../../../../models/kpi/kpi-to-display";
import {KpiService} from "../../../../../serivce/kpi.service";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-mile-stone-element',
  templateUrl: './mile-stone-element.component.html',
  styleUrls: ['./mile-stone-element.component.css']
})
export class MileStoneElementComponent implements OnInit, OnDestroy {

  @Input()
  mileStone: MileStoneToDisplay;

  kpis: KpiToDisplay[];

  private subscriptions: Subscription[] = [];

  constructor(private kpiService: KpiService) {
  }

  ngOnDestroy(): void {
    for (let subscription of this.subscriptions) {
      subscription.unsubscribe();
    }
  }

  getStatus(mileStone: MileStoneToDisplay): number {
    const startDate = new Date(mileStone.startDate).getTime();
    const endDate = new Date(mileStone.finishDate).getTime();
    const now = new Date().getTime();

    const q = now - startDate;
    const d = endDate - startDate;

    return Math.round((q / d) * 100);
  }

  ngOnInit(): void {
    const getAllKpisByMileStoneIdSubscription = this.kpiService.getKpisByMileStoneId(+this.mileStone.id)
      .subscribe(value => this.kpis = value);

    this.subscriptions.push(getAllKpisByMileStoneIdSubscription);
  }
}

