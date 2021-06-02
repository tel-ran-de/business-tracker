import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {TaskService} from '../../../../serivce/task.service';
import {TaskToDisplay} from '../../../../models/task/task-to-display';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';

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

  productId: number;

  tasks: TaskToDisplay[];
  private subscriptions: Subscription[] = [];

  constructor(private taskService: TaskService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const getTaskByIdSubscription = this.taskService
      .getAllByParams(this.roadMapId, 'roadMapId').subscribe(value => this.tasks = value);

    this.productId = +this.route.snapshot.paramMap.get('productId');
    this.subscriptions.push(getTaskByIdSubscription);
  }

  getStatus(task: TaskToDisplay): number {
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
