import {Component, Input} from '@angular/core';
import {MileStoneToDisplay} from "../../../../../models/mile-stone/mile-stone-to-display";
import {KpiToDisplay} from "../../../../../models/kpi/kpi-to-display";


@Component({
  selector: 'app-mile-stone-element',
  templateUrl: './mile-stone-element.component.html',
  styleUrls: ['./mile-stone-element.component.css']
})
export class MileStoneElementComponent {

  @Input()
  mileStone: MileStoneToDisplay;
  @Input()
  kpis: KpiToDisplay[];

  constructor() {
  }

  getStatus(task: MileStoneToDisplay): number {
    const startDate = new Date(task.startDate).getTime();
    const endDate = new Date(task.finishDate).getTime();
    const now = new Date().getTime();

    const q = now - startDate;
    const d = endDate - startDate;

    return Math.round((q / d) * 100);
  }
}

