import {Component, OnDestroy, OnInit} from '@angular/core';
import {TaskToDisplay} from '../../../../models/task/task-to-display';
import {ActivatedRoute} from '@angular/router';
import {TaskService} from '../../../../serivce/task.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-sprint',
  templateUrl: './sprint.component.html',
  styleUrls: ['./sprint.component.css']
})
export class SprintComponent implements OnInit, OnDestroy {

  task: TaskToDisplay;
  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute, private taskService: TaskService) {
  }

  ngOnInit(): void {
    const taskId = this.route.snapshot.paramMap.get('taskId');
    const getTaskSub = this.taskService.getById(taskId)
      .subscribe(value => this.task = value);

    this.subscriptions.push(getTaskSub);
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
