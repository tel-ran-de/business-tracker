import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MileStoneService} from '../../../../serivce/mile-stone.service';
import {MileStoneToDisplay} from '../../../../models/mile-stone/mile-stone-to-display';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-time-line',
  templateUrl: './time-line.component.html',
  styleUrls: ['./time-line.component.css']
})
export class TimeLineComponent implements OnInit, OnDestroy {

  @Input()
  roadMapId: string;
  @Input()
  horizontal = false;
  @Input()
  hover = true;
  @Input()
  height = 60;

  mileStones: MileStoneToDisplay[];
  private subscriptions: Subscription[] = [];

  constructor(private mileStoneService: MileStoneService) {
  }

  ngOnInit(): void {
    const getMileStoneByRoadMapIdSubscription = this.mileStoneService
      .getAllByParams(this.roadMapId, 'roadMapId').subscribe(value => this.mileStones = value);
    this.subscriptions.push(getMileStoneByRoadMapIdSubscription);
  }

  getStatus(task: MileStoneToDisplay): number {
    const startDate = new Date(task.startDate).getTime();
    const endDate = new Date(task.finishDate).getTime();
    const now = new Date().getTime();

    const q = now - startDate;
    const d = endDate - startDate;

    return Math.round((q / d) * 100);
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      if (subscription) {
        subscription.unsubscribe();
      }
    }
  }
}
