import {Component, OnDestroy, OnInit} from '@angular/core';
import {MileStoneToDisplay} from '../../../../models/mile-stone/mile-stone-to-display';
import {ActivatedRoute} from '@angular/router';
import {MileStoneService} from '../../../../serivce/mile-stone.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit, OnDestroy {

  mileStone: MileStoneToDisplay;
  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private taskService: MileStoneService) {
  }

  ngOnInit(): void {
    const mileStoneId = +this.route.snapshot.paramMap.get('mileStoneId');
    const getTaskSub = this.taskService.getMileStoneById(mileStoneId)
      .subscribe(value => this.mileStone = value);

    this.subscriptions.push(getTaskSub);
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
